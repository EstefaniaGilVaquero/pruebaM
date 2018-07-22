package com.asmes.meniere.activity.Login;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.asmes.meniere.R;
import com.asmes.meniere.activity.BaseActivity;
import com.asmes.meniere.activity.TabsActivity;
import com.asmes.meniere.activity.UtilitiesMeniere.HearingDiaryActivity;
import com.asmes.meniere.models.Mail;
import com.asmes.meniere.prefs.UserSession;
import com.asmes.meniere.utils.Constants;
import com.asmes.meniere.utils.FingerprintHandler;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class LoginFingerTipActivity extends BaseActivity {

    //Fingertip
    // Declare a string variable for the key we’re going to use in our fingerprint authentication
    private static final String KEY_NAME = "yourKey";
    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    static LoginFingerTipActivity loginFingerTipActivity;

    //UI references
    private EditText mPassEt;
    private Button mLoginBtn;
    private TextView mRememberPassTxt, mResetEmailPassTxt;
    private ImageView fingerTipImageView;
    private TextView loginInfo2;
    private Activity mActivity;

    public static LoginFingerTipActivity getInstance(){
        return loginFingerTipActivity;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_finger_tip);


        mActivity = this;
        loginFingerTipActivity = this;

        mPassEt = findViewById(R.id.currentPassEditText);
        mLoginBtn = findViewById(R.id.loginBtn);
        mRememberPassTxt = findViewById(R.id.rememberPass);
        mResetEmailPassTxt = findViewById(R.id.resetEmailPass);
        fingerTipImageView = findViewById(R.id.fingerTipImageView);
        loginInfo2 = findViewById(R.id.info2TextView);

        // If you’ve set your app’s minSdkVersion to anything lower than 23, then you’ll need to verify that the device is running Marshmallow
        // or higher before executing any fingerprint-related code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Get an instance of KeyguardManager and FingerprintManager//
            keyguardManager = (KeyguardManager) mActivity.getSystemService(KEYGUARD_SERVICE);
            fingerprintManager = (FingerprintManager) mActivity.getSystemService(FINGERPRINT_SERVICE);

            //textView = (TextView) findViewById(R.id.textview);

            //Check whether the device has a fingerprint sensor//
            if (!fingerprintManager.isHardwareDetected()) {
                // If a fingerprint sensor isn’t available, then inform the user that they’ll be unable to use your app’s fingerprint functionality//
                String message =  "Your device doesn't support fingerprint authentication";
                Toast.makeText(mActivity,message,Toast.LENGTH_LONG).show();
                hideFingerTipViews();
            }else {
                //Check whether the user has granted your app the USE_FINGERPRINT permission//
                if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                    // If your app doesn't have this permission, then display the following text//
                    String message = "Please enable the fingerprint permission";
                    Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
                    hideFingerTipViews();
                }

                //Check that the user has registered at least one fingerprint//
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    // If the user hasn’t configured any fingerprints, then display the following message//
                    String message = "No fingerprint configured. Please register at least one fingerprint in your device's Settings";
                    Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
                    hideFingerTipViews();
                }

                //Check that the lockscreen is secured//
                if (!keyguardManager.isKeyguardSecure()) {
                    // If the user hasn’t secured their lockscreen with a PIN password or pattern, then display the following text//
                    String message = "Please enable lockscreen security in your device's Settings";
                    Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
                    hideFingerTipViews();
                } else {
                    try {
                        generateKey();
                    } catch (FingerprintException e) {
                        e.printStackTrace();
                    }

                    if (initCipher()) {
                        //If the cipher is initialized successfully, then create a CryptoObject instance//
                        cryptoObject = new FingerprintManager.CryptoObject(cipher);
                        // Here, I’m referencing the FingerprintHandler class that we’ll create in the next section. This class will be responsible
                        // for starting the authentication process (via the startAuth method) and processing the authentication process events//
                        FingerprintHandler helper = new FingerprintHandler(mActivity);
                        helper.startAuth(fingerprintManager, cryptoObject);
                    }
                }
            }
        }

        mRememberPassTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = UserSession.getInstance(mActivity).getPreferences().getString(UserSession.PREFERENCES_EMAIL, "");
                String content = getString(R.string.rememberPassContent) + "" + email;
                new MaterialDialog.Builder(mActivity)
                        .content(content)
                        .positiveText(R.string.positiveTxtOk)
                        .negativeText(R.string.negativeTxtCancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //Send email
                                sendEmail();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        mResetEmailPassTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabsActivity.activitySwitchFlag = true;
                startActivity(new Intent(view.getContext(), EditUserDataActivity.class));
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = "";

                if (mPassEt.getText().toString().equals("")){
                    message = getString(R.string.emptyPass);
                }else if(!UserSession.getInstance(mActivity).getPreferences().getString(UserSession.PREFERENCES_PASS, "").equals(mPassEt.getText().toString())){
                    message = getString(R.string.invalidPass);
                }else {

                    UserSession.getInstance(mActivity).setmIsLoggedIn(true);

                    TabsActivity.activitySwitchFlag = true;
                    startActivity(new Intent(view.getContext(), HearingDiaryActivity.class));

                }

                if (!message.equals("")) Toast.makeText(mActivity,message,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void hideFingerTipViews(){
        fingerTipImageView.setVisibility(View.GONE);
        loginInfo2.setText(getString(R.string.loginInfo3));
    }

    //Create the generateKey method that we’ll use to gain access to the Android keystore and generate the encryption key//
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateKey() throws FingerprintException {
        try {
            // Obtain a reference to the Keystore using the standard Android keystore container identifier (“AndroidKeystore”)//
            keyStore = KeyStore.getInstance("AndroidKeyStore");

            //Generate the key//
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            //Initialize an empty KeyStore//
            keyStore.load(null);

            //Initialize the KeyGenerator//
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyGenerator.init(new
                        //Specify the operation(s) this key can be used for//
                        KeyGenParameterSpec.Builder(KEY_NAME,
                        KeyProperties.PURPOSE_ENCRYPT |
                                KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)

                        //Configure this key so that the user has to confirm their identity with a fingerprint each time they want to use it//
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build());

                //Generate the key//
                keyGenerator.generateKey();
            }

        } catch (Exception exc) {
            if (exc instanceof KeyStoreException
                    | exc instanceof NoSuchAlgorithmException
                    | exc instanceof NoSuchProviderException
                    | exc instanceof InvalidAlgorithmParameterException
                    | exc instanceof CertificateException
                    | exc instanceof IOException) {
                exc.printStackTrace();
                throw new FingerprintException(exc);
            }
        }
    }

    //Create a new method that we’ll use to initialize our cipher//
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean initCipher() {
        try {
            //Obtain a cipher instance and configure it with the properties required for fingerprint authentication//
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //Return true if the cipher has been initialized successfully//
            return true;
        } catch (Exception exc) {
            if(exc instanceof KeyStoreException
                    | exc instanceof CertificateException
                    | exc instanceof UnrecoverableKeyException
                    | exc instanceof IOException
                    | exc instanceof NoSuchAlgorithmException
                    | exc instanceof InvalidKeyException
                    | exc instanceof KeyPermanentlyInvalidatedException){
                throw new RuntimeException("Failed to init Cipher", exc);
            }
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private class FingerprintException extends Exception {
        public FingerprintException(Exception e) {
            super(e);
        }
    }

    private void sendEmail() {
        String[] destinatary = {UserSession.getInstance(mActivity).getPreferences().getString(UserSession.PREFERENCES_EMAIL, "")};
        SendEmailAsyncTask email = new SendEmailAsyncTask();

        //email.activity = this;
        email.m = new Mail(Constants.SENDER_EMAIL_USER, Constants.SENDER_EMAIL_PASS);
        email.m.set_from(Constants.SENDER_EMAIL_FROM);
        email.m.setBody(UserSession.getInstance(mActivity).getPreferences().getString(UserSession.PREFERENCES_PASS, ""));
        email.m.set_to(destinatary);
        email.m.set_subject("Meniere app, remember password");
        email.execute();
    }

}