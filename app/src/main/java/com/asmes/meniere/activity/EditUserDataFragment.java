package com.asmes.meniere.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asmes.meniere.R;
import com.asmes.meniere.activity.MyMeniere.OneFragment;
import com.asmes.meniere.models.User;
import com.asmes.meniere.prefs.UserSession;
import static com.asmes.meniere.utils.Utils.isValidMail;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserDataFragment extends Fragment {

    private View rootView;
    private User mUserLogged;

    //UI references
    private EditText mCurrentPass, mNewEmail, mNewPass, mConfirmNewPass;
    private Button mCancelBtn, mSaveBtn;

    public EditUserDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_edit_user_data, container, false);

        mCurrentPass = rootView.findViewById(R.id.currentPassEditText);
        mNewEmail = rootView.findViewById(R.id.newEmailEditText);
        mNewPass = rootView.findViewById(R.id.newPassEditText);
        mConfirmNewPass = rootView.findViewById(R.id.newConfirmPassEditText);
        mCancelBtn = rootView.findViewById(R.id.cancelBtn);
        mSaveBtn = rootView.findViewById(R.id.saveBtn);


        mCurrentPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(UserSession.getInstance(getContext()).getPreferences().getString(UserSession.PREFERENCES_PASS, "").equals(mCurrentPass.getText().toString())){
                    Toast.makeText(getContext(),getString(R.string.editUserOkCurrentPass),Toast.LENGTH_LONG).show();
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
                if (!mNewEmail.isEnabled()) Toast.makeText(getContext(),getString(R.string.editUserInvalidCurrentPass),Toast.LENGTH_LONG).show();

            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
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
                    UserSession.getInstance(getContext()).editUser(mUserLogged);

                    goToLogin();
                }

                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });

        return rootView;
    }

    public void goToLogin(){
        Fragment fragment = LoginFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutEditUserFragment, fragment);
        transaction.commit();
    }
}
