package com.dax.demo.fingerprint;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Desc:非对称加密对象帮助类
 * Created by liuxiong on 2017/5/8.
 * Email:liuxiong@corp.netease.com
 */
public class AsymmetricCryptoObjectHelper {
    /** Alias for our key in the Android Key Store */
    public static final String KEY_NAME = "my_key";
    static final String KEYSTORE_NAME = "AndroidKeyStore";
    static final String SIGNATURE_FORM = "SHA256withECDSA";
    KeyStore mKeyStore;
    KeyPairGenerator mKeyPairGenerator;
    Signature mSignature;
    public AsymmetricCryptoObjectHelper(){
        try {
            mKeyStore = KeyStore.getInstance(KEYSTORE_NAME);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        try {
            mKeyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC,KEYSTORE_NAME);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            mSignature = Signature.getInstance(SIGNATURE_FORM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        createKeyPair();
        initSignature();
    }

    public FingerprintManagerCompat.CryptoObject buildCryptoObject(){
        return new FingerprintManagerCompat.CryptoObject(mSignature);
    }

    /**
     * Initialize the {@link Signature} instance with the created key in the
     * {@link #createKeyPair()} method.
     *
     * @return {@code true} if initialization is successful, {@code false} if the lock screen has
     * been disabled or reset after the key was generated, or if a fingerprint got enrolled after
     * the key was generated.
     */
    private boolean initSignature() {
        try {
            mKeyStore.load(null);
            PrivateKey key = (PrivateKey) mKeyStore.getKey(KEY_NAME, null);
            mSignature.initSign(key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    /**
     * Generates an asymmetric key pair in the Android Keystore. Every use of the private key must
     * be authorized by the user authenticating with fingerprint. Public key use is unrestricted.
     */
    public void createKeyPair() {
        // The enrolling flow for fingerprint. This is where you ask the user to set up fingerprint
        // for your flow. Use of keys is necessary if you need to know if the set of
        // enrolled fingerprints has changed.
        try {
            // Set the alias of the entry in Android KeyStore where the key will appear
            // and the constrains (purposes) in the constructor of the Builder
            mKeyPairGenerator.initialize(
                    new KeyGenParameterSpec.Builder(KEY_NAME,
                            KeyProperties.PURPOSE_SIGN)
                            .setDigests(KeyProperties.DIGEST_SHA256)
                            .setAlgorithmParameterSpec(new ECGenParameterSpec("secp256r1"))
                            // Require the user to authenticate with a fingerprint to authorize
                            // every use of the private key
                            .setUserAuthenticationRequired(true)
                            .build());
            mKeyPairGenerator.generateKeyPair();
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    public PublicKey getPublicKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KEYSTORE_NAME);
            keyStore.load(null);
            PublicKey publicKey = keyStore.getCertificate(KEY_NAME).getPublicKey();
            Log.d("PublicKey","1---> "+publicKey.toString());
            // Provide the public key to the backend. In most cases, the key needs to be transmitted
            // to the backend over the network, for which Key.getEncoded provides a suitable wire
            // format (X.509 DER-encoded). The backend can then create a PublicKey instance from the
            // X.509 encoded form using KeyFactory.generatePublic. This conversion is also currently
            // needed on API Level 23 (Android M) due to a platform bug which prevents the use of
            // Android Keystore public keys when their private keys require user authentication.
            // This conversion creates a new public key which is not backed by Android Keystore and
            // thus is not affected by the bug.
            KeyFactory factory = KeyFactory.getInstance(publicKey.getAlgorithm());
            X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey.getEncoded());
            PublicKey verificationKey = factory.generatePublic(spec);
            Log.d("PublicKey","2---> "+ Base64.encodeToString(verificationKey.getEncoded(),Base64.DEFAULT));
            return verificationKey;
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException |
                IOException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 模拟服务端用公钥对数据进行校验
     * @param publicKey
     * @param origindata 原始数据
     * @param signatureData 客户端签名后的数据
     * @return
     */
    public boolean verifyByServer(PublicKey publicKey,byte[]origindata,byte[]signatureData){
        try {
            Signature verificationFunction = Signature.getInstance(SIGNATURE_FORM);
            verificationFunction.initVerify(publicKey);
            verificationFunction.update(origindata);
            if (verificationFunction.verify(signatureData)) {
                // Transaction is verified with the public key associated with the user
                // Do some post purchase processing in the server
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 模拟客户端指纹识别成功后对数据进行签名
     * @param cryptoObject
     */
    public byte[] verifyByClient(FingerprintManagerCompat.CryptoObject cryptoObject,byte[]originData){
        Signature signature = cryptoObject.getSignature();
        // Include a client nonce in the transaction so that the nonce is also signed by the private
        // key and the backend can verify that the same nonce can't be used to prevent replay
        // attacks.
        try {
            signature.update(originData);
            byte[] sigBytes = signature.sign();
            return sigBytes;
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }
}
