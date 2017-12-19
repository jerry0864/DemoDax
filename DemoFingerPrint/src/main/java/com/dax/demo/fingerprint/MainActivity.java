package com.dax.demo.fingerprint;

import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.crypto.Cipher;

/**
 * refrerrence:
 * http://blog.csdn.net/wl9739/article/details/52444671
 * http://blog.csdn.net/lintcgirl/article/details/51355203
 * http://blog.csdn.net/createchance/article/details/51991764
 *
 * Desc:MainActivity
 * Created by liuxiong on 2017/5/9.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "FingerPrintRecognize";
    private Button mBtnScanner,mBtnCancel;
    private FingerprintManagerCompat mFingerManager;
    private KeyguardManager mKeyguardManager;
    private CancellationSignal mCancelSignal;
    private TextView mTvInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnScanner = (Button) findViewById(R.id.btn_scanner);
        mBtnCancel = (Button) findViewById(R.id.btn_cancel);
        findViewById(R.id.btn_write_info).setOnClickListener(this);
        findViewById(R.id.btn_read_info).setOnClickListener(this);
        mTvInfo = (TextView)findViewById(R.id.text_info);
        mBtnScanner.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        initFingerPrintMgr();
    }

    private void initFingerPrintMgr() {
        //获取指纹管理者对象
        mFingerManager = FingerprintManagerCompat.from(this);
        mKeyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_scanner:
                //startFingerRecognize();
                break;
            case R.id.btn_cancel:
                //cancelFingerRecognize();
                break;
            case R.id.btn_write_info:
                showDialog("您将要存入密码："+PWD);
                startFingerRecognize(Cipher.ENCRYPT_MODE);
                write = true;
                break;
            case R.id.btn_read_info:
                showDialog("");
                startFingerRecognize(Cipher.DECRYPT_MODE);
                write = false;
                break;
        }
    }
    private static final String PWD = "123456";
    private AlertDialog dialog;
    private boolean write;
    private void showDialog(String message) {
        dialog = new AlertDialog.Builder(this).setTitle("请进行指纹识别")
                .setMessage(message)
                .create();
        dialog.show();

    }

    private void cancelFingerRecognize() {
        if(mCancelSignal!=null){
            mCancelSignal.cancel();
            mCancelSignal = null;
            setRecoginzeState(true);
        }
    }

    private void startFingerRecognize(int type) {
        //是否支持指纹识别
        boolean supported = mFingerManager.isHardwareDetected();
        //Log.d(TAG,"hardware support fingerprint function --> "+supported);
        if(!supported){
            showToast("该设备不支持指纹识别");
            return;
        }
        //检测用户是否设置了密码、图案或其他锁屏，否则先要设置才能使用指纹识别（安卓安全机制）
        if(!mKeyguardManager.isKeyguardSecure()){
            showToast("请先设置锁屏");
            return;
        }
        //是否已经有录入过指纹
        boolean hasEnrolled = mFingerManager.hasEnrolledFingerprints();
        //Log.d(TAG,"has enrolled fingerprint --> "+hasEnrolled);
        if(!hasEnrolled){
            showToast("该设备暂没有登记指纹，请去设置界面录入指纹");
            startActivity(new Intent(Settings.ACTION_SETTINGS));
            return;
        }

        //识别指纹
        try {
            scannerPrint(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRecoginzeState(false);
    }

    /**设置操作按钮的状态*/
    private void setRecoginzeState(boolean flag) {
        if(flag){
            mBtnScanner.setEnabled(true);
            mBtnCancel.setEnabled(false);
        }else{
            mBtnScanner.setEnabled(false);
            mBtnCancel.setEnabled(true);
        }

    }

    private void scannerPrint(int type) throws Exception{
        mCancelSignal = new CancellationSignal();
        //用对称加密对象
        SymmetricCryptoObjectHelper cryptoHelper = new SymmetricCryptoObjectHelper(MainActivity.this);
        FingerprintManagerCompat.CryptoObject cryptoObject = cryptoHelper.buildCryptoObject(type);
        //使用非对称性加密对象
//        AsymmetricCryptoObjectHelper cryptoHelper = new AsymmetricCryptoObjectHelper();
//        FingerprintManagerCompat.CryptoObject cryptoObject = cryptoHelper.buildCryptoObject();
      //Log.d(TAG,"cryptoObject --> "+(cryptoObject==null?null:cryptoObject.toString()));
        mFingerManager.authenticate(cryptoObject,0, mCancelSignal, mCallback,null);
    }

    String encryptText;
    private FingerprintManagerCompat.AuthenticationCallback mCallback = new FingerprintManagerCompat.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            Log.d(TAG,errMsgId+" -- "+ errString);
            showToast(errMsgId+" -- "+ errString);
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
            Log.d(TAG,helpMsgId+" -- "+ helpString);
            showToast(helpMsgId+" -- "+ helpString);
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            Log.d(TAG,"onAuthenticationSucceeded--> "+result.getCryptoObject().toString());
            showToast("指纹识别成功");
            if(dialog!=null){
                dialog.dismiss();
            }
            FingerprintManagerCompat.CryptoObject obj = result.getCryptoObject();
            Cipher cipher = obj.getCipher();
            try {
                if(write){
                    byte[] encrypted = cipher.doFinal(PWD.getBytes());
                    byte[] IVByte = cipher.getIV();

                    //String se = Base64.encodeToString(encrypted, Base64.URL_SAFE);
                    String iv = Base64.encodeToString(IVByte, Base64.DEFAULT);
                    getSharedPreferences("temp",MODE_PRIVATE).edit().putString("iv",iv).commit();
                    encryptText = Base64.encodeToString(encrypted, Base64.DEFAULT /* flags */);
                    Log.d(TAG,"onAuthenticationSucceeded-encrypted-> "+encryptText);
                    mTvInfo.setText(encryptText);
                }else{
                    Log.d(TAG,"onAuthenticationSucceeded-decrypted-> "+encryptText);
                    byte[] decode = Base64.decode(encryptText,Base64.DEFAULT);
                    byte[] decrypted = cipher.doFinal(decode);
                    String pwd = new String(decrypted);
                    Log.d(TAG,"onAuthenticationSucceeded-decrypted-> "+pwd);
                    mTvInfo.setText(pwd);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            Log.d(TAG,"onAuthenticationFailed");
            showToast("指纹识别失败");
        }
    };

    private void showToast(String content){
        Toast.makeText(this,content,Toast.LENGTH_LONG).show();
    }
}
