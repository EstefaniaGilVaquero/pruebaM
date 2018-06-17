package com.asmes.meniere.utils;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v13.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.asmes.meniere.R;
import com.asmes.meniere.activity.Login.LoginFingerTipActivity;
import com.asmes.meniere.activity.MyMeniere.OneFragment;
import com.asmes.meniere.activity.TabsActivity;
import com.asmes.meniere.activity.UtilitiesMeniere.HearingDiaryActivity;
import com.asmes.meniere.prefs.UserSession;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private CancellationSignal cancellationSignal;
    private Context context;
    private FragmentManager fragmentManager;
    private String activityToGo;
    private LoginFingerTipActivity loginFingerTipActivity = new LoginFingerTipActivity();


    // Constructor
    public FingerprintHandler(Context mContext, FragmentManager mFragmentManager) {
        context = mContext;
        fragmentManager = mFragmentManager;
        activityToGo = "MyMeniere";
    }

    public FingerprintHandler(Context mContext) {
        context = mContext;
        activityToGo = "HearingDiary";
    }


    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }


    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString, false);
    }


    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString, false);
    }


    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.", false);
    }


    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Fingerprint Authentication succeeded.", true);
    }


    public void update(String e, Boolean success){
            Toast.makeText(context, e, Toast.LENGTH_LONG).show();
            if(success){
                UserSession.getInstance(context).setmIsLoggedIn(true);
                if (activityToGo.equalsIgnoreCase("MyMeniere")) {
                    Fragment fragment = new OneFragment();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.layoutLoginFragment, fragment);
                    transaction.commit();
                }else if(activityToGo.equalsIgnoreCase("HearingDiary")){
                    TabsActivity.activitySwitchFlag = true;
                    context.startActivity(new Intent(context, HearingDiaryActivity.class));
                }
            }
    }

}
