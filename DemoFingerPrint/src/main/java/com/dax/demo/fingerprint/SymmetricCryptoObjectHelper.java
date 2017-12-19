package com.dax.demo.fingerprint;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Base64;
import android.util.Log;

import java.security.Key;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * Desc:对称加密对象生成帮助类
 * Created by liuxiong on 2017/5/8.
 */

public class SymmetricCryptoObjectHelper {
    // This can be key name you want. Should be unique for the app.
    static final String KEY_NAME = "com.dax.demo.fingerprint_authentication_key";

    // We always use this keystore on Android.
    static final String KEYSTORE_NAME = "AndroidKeyStore";

    // Should be no need to change these values.
    static final String KEY_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;
    static final String BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC;
    static final String ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7;
    static final String TRANSFORMATION = KEY_ALGORITHM + "/" +
            BLOCK_MODE + "/" +
            ENCRYPTION_PADDING;
    final KeyStore mKeyStore;
    Context mContext;
    public SymmetricCryptoObjectHelper(Context context) throws Exception {
        this.mContext = context;
        mKeyStore = KeyStore.getInstance(KEYSTORE_NAME);
        mKeyStore.load(null);
    }
    int mType;
    public FingerprintManagerCompat.CryptoObject buildCryptoObject(int type) throws Exception {
        this.mType = type;
        Cipher cipher = createCipher(true);
        return new FingerprintManagerCompat.CryptoObject(cipher);
    }

    Cipher createCipher(boolean retry) throws Exception {
        Key key = getKey();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        if(mType == Cipher.DECRYPT_MODE){
            String iv = mContext.getSharedPreferences("temp",MODE_PRIVATE).getString("iv",null);
            cipher.init(Cipher.DECRYPT_MODE , key,new IvParameterSpec(Base64.decode(iv,Base64.DEFAULT)));
        }else{
            cipher.init(Cipher.ENCRYPT_MODE , key);
        }
        Log.d("liuxiong","111---> "+cipher.toString()) ;
//        try {
//        } catch (KeyPermanentlyInvalidatedException e) {
//            mKeyStore.deleteEntry(KEY_NAME);
//            if (retry) {
//                createCipher(false);
//            } else {
//                throw new Exception("Could not create the cipher for fingerprint authentication.", e);
//            }
//        }
        return cipher;
    }

    Key getKey() throws Exception {
        Key secretKey;
        if (!mKeyStore.isKeyEntry(KEY_NAME)) {
            return createKey();
        }

        secretKey = mKeyStore.getKey(KEY_NAME, null);
        return secretKey;
    }

    Key createKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM, KEYSTORE_NAME);
        KeyGenParameterSpec keyGenSpec =
                new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(BLOCK_MODE)
                        .setEncryptionPaddings(ENCRYPTION_PADDING)
                        .setUserAuthenticationRequired(true)
                        .build();
        keyGen.init(keyGenSpec);
        return keyGen.generateKey();
    }

    /**
     * 产生及还原对称秘钥
     * @throws Exception
     */
    public void generateAndResetKey() throws Exception {
        //对称key即SecretKey创建和导入，假设双方约定使用DES算法来生成对称密钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        //设置密钥长度。注意，每种算法所支持的密钥长度都是不一样的。DES只支持64位长度密钥
        keyGenerator.init(64);
        //生成SecretKey对象，即创建一个对称密钥，并获取二进制的书面表达
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyData = secretKey.getEncoded();
        //日常使用时，一般会把上面的二进制数组通过Base64编码转换成字符串，然后发给使用者
        String keyInBase64 = Base64.encodeToString(keyData, Base64.DEFAULT);


        //假设对方收到了base64编码后的密钥，首先要得到其二进制表达式，用二进制数组构造KeySpec对象。对称key使用SecretKeySpec类
        byte[] receivedKeyData = Base64.decode(keyInBase64, Base64.DEFAULT);
        SecretKeySpec keySpec = new SecretKeySpec(receivedKeyData, "DES");
        //创建对称Key导入用的SecretKeyFactory
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        //根据KeySpec还原Key对象，即把key的书面表达式转换成了Key对象
        SecretKey receivedKeyObject = secretKeyFactory.generateSecret(keySpec);
        byte[] encodedReceivedKeyData = receivedKeyObject.getEncoded();
        Log.e(TAG, "==>secret key: received key encoded data =" + Utils.bytesToHexString(encodedReceivedKeyData));
    }
}
