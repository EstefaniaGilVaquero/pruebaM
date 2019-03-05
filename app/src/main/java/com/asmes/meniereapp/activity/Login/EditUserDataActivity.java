package com.asmes.meniereapp.activity.Login;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asmes.meniereapp.R;
import com.asmes.meniereapp.activity.BaseActivity;
import com.asmes.meniereapp.models.User;
import com.asmes.meniereapp.prefs.UserSession;

import static com.asmes.meniereapp.utils.Utils.isValidMail;

public class EditUserDataActivity extends BaseActivity {

    private User mUserLogged;
    private Activity activity;
    private static String TAG;

    //UI references
    private EditText mCurrentPass, mNewEmail, mNewPass, mConfirmNewPass;
    private Button mCancelBtn, mSaveBtn;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_data);

        TAG = getString(R.string.editUser);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(TAG);

        activity = this;

        mCurrentPass = findViewById(R.id.currentPassEditText);
        mNewEmail = findViewById(R.id.newEmailEditText);
        mNewPass = findViewById(R.id.newPassEditText);
        mConfirmNewPass = findViewById(R.id.newConfirmPassEditText);
        mCancelBtn = findViewById(R.id.cancelBtn);
        mSaveBtn = findViewById(R.id.saveBtn);

        mCurrentPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(UserSession.getInstance(activity).getPreferences().getString(UserSession.PREFERENCES_PASS, "").equals(mCurrentPass.getText().toString())){
                    Toast.makeText(activity,getString(R.string.editUserOkCurrentPass),Toast.LENGTH_LONG).show();
                    /*if(!mNewEmail.getText().toString().equals("") || !mNewPass.getText().toString().equals("")) mSaveBtn.setEnabled(true);
                    else mSaveBtn.setEnabled(false);*/
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mNewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mNewEmail.isEnabled()) Toast.makeText(activity,getString(R.string.editUserInvalidCurrentPass),Toast.LENGTH_LONG).show();

            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                Boolean ok = false;
                if (mCurrentPass.getText().toString().equals("")) {
                    message = getString(R.string.editUserInvalidCurrentPass);
                }else if(!mNewEmail.getText().toString().equals("") && !mNewPass.getText().toString().equals("")){
                    message = getString(R.string.editUserAnyChange);
                } else if (!mNewEmail.getText().toString().equals("") && !isValidMail(mNewEmail.getText().toString())){
                    message = getString(R.string.invalidEmail);
                }else if(!mNewPass.getText().toString().equals(mConfirmNewPass.getText().toString())){
                    message = getString(R.string.differentConfirmPass);
                }else {
                    message = getString(R.string.reditUserSuccess);
                    mUserLogged = new User(mNewEmail.getText().toString(), mNewPass.getText().toString());
                    UserSession.getInstance(activity).editUser(mUserLogged);
                    ok = true;
                }

                Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
                if (ok) finish();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}