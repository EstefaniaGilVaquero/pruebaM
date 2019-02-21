package com.asmes.meniereapp.activity.Login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asmes.meniereapp.R;
import com.asmes.meniereapp.activity.BaseActivity;
import com.asmes.meniereapp.activity.TabsActivity;
import com.asmes.meniereapp.activity.UtilitiesMeniere.HearingDiaryActivity;
import com.asmes.meniereapp.models.User;
import com.asmes.meniereapp.prefs.UserSession;

import static com.asmes.meniereapp.utils.Utils.isValidMail;

public class RegisterActivity extends BaseActivity {
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private User mUserLogged;

    //UI references
    private EditText mEmail, mPass, mConfirmPass;
    private Button mRegisterBtn;
    private Activity activity;
    private static String TAG;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TAG = getString(R.string.singup);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(TAG);

        activity = this;

        mEmail = findViewById(R.id.newEmailEditText);
        mPass = findViewById(R.id.currentPassEditText);
        mConfirmPass = findViewById(R.id.newConfirmPassEditText);
        mRegisterBtn = findViewById(R.id.registerBtn);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            String message;

            if (mEmail.getText().toString().equals("")){
                message = getString(R.string.emptyEmail);
            }else if (!isValidMail(mEmail.getText().toString())){
                message = getString(R.string.invalidEmail);
            }else if(mPass.getText().toString().equals("")){
                message = getString(R.string.emptyPass);
            }else if(!mPass.getText().toString().equals(mConfirmPass.getText().toString())){
                message = getString(R.string.differentConfirmPass);
            }else {
                message = getString(R.string.registerSuccess);
                mUserLogged = new User(mEmail.getText().toString(), mPass.getText().toString());
                UserSession.getInstance(activity).saveUser(mUserLogged);

                TabsActivity.activitySwitchFlag = true;
                startActivity(new Intent(view.getContext(), HearingDiaryActivity.class));
            }

            Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}
