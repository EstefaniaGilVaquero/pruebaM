package com.asmes.meniereapp.activity.Login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asmes.meniereapp.R;
import com.asmes.meniereapp.activity.MyMeniere.OneFragment;
import com.asmes.meniereapp.models.User;
import com.asmes.meniereapp.prefs.UserSession;
import static com.asmes.meniereapp.utils.Utils.isValidMail;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private View rootview;
    private User mUserLogged;

    //UI references
    private EditText mEmail, mPass, mConfirmPass;
    private Button mRegisterBtn;


    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        /*Bundle bundle = new Bundle();
        bundle.putString(Constants.ITEM_TYPE, itemType);*/
        RegisterFragment fragment = new RegisterFragment();
        //fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_register, container, false);

        mEmail = rootview.findViewById(R.id.newEmailEditText);
        mPass = rootview.findViewById(R.id.currentPassEditText);
        mConfirmPass = rootview.findViewById(R.id.newConfirmPassEditText);
        mRegisterBtn = rootview.findViewById(R.id.registerBtn);

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
                     UserSession.getInstance(getContext()).saveUser(mUserLogged);

                    Fragment fragment = new OneFragment();
                    FragmentManager fragmentManager = getChildFragmentManager();
                    fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.layoutRegisterFragment, fragment);
                    transaction.addToBackStack(BACK_STACK_ROOT_TAG);
                    transaction.commit();

                     //Go to myMeniereFragment
                }

                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }
        });
    return rootview;

    }

}


