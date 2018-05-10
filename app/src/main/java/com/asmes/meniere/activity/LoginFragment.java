package com.asmes.meniere.activity;



import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.asmes.meniere.R;
import com.asmes.meniere.activity.MyMeniere.OneFragment;
import com.asmes.meniere.models.Mail;
import com.asmes.meniere.models.User;
import com.asmes.meniere.prefs.UserSession;
import com.asmes.meniere.utils.Constants;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private View rootView;

    //UI references
    private EditText mPassEt;
    private Button mLoginBtn;
    private TextView mRememberPassTxt, mResetEmailPassTxt;
    private Activity mActivity;
    private Context mContext;


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        /*Bundle bundle = new Bundle();
        bundle.putString(Constants.ITEM_TYPE, itemType);*/
        LoginFragment fragment = new LoginFragment();
        //fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        mContext = getActivity();

        mPassEt = rootView.findViewById(R.id.loginPassEditText);
        mLoginBtn = rootView.findViewById(R.id.loginBtn);
        mRememberPassTxt = rootView.findViewById(R.id.rememberPass);
        mResetEmailPassTxt = rootView.findViewById(R.id.resetEmailPass);

        mRememberPassTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = UserSession.getInstance(getContext()).getPreferences().getString(UserSession.PREFERENCES_EMAIL, "");
                String content = getString(R.string.rememberPassContent) + "" + email;
                new MaterialDialog.Builder(getContext())
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

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = "";

                if (mPassEt.getText().toString().equals("")){
                    message = getString(R.string.emptyPass);
                }else if(!UserSession.getInstance(getContext()).getPreferences().getString(UserSession.PREFERENCES_PASS, "").equals(mPassEt.getText().toString())){
                    message = getString(R.string.invalidPass);
                }else {

                    UserSession.getInstance(getContext()).setmIsLoggedIn(true);

                    //TODO choose between fragments
                    //Go to myMeniereFragment or hearingDiary

                    Fragment fragment = OneFragment.newInstance();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.layoutLoginFragment, fragment);
                    transaction.commit();

                }

                if (!message.equals("")) Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

    private void sendEmail() {
        String[] destinatary = {UserSession.getInstance(getContext()).getPreferences().getString(UserSession.PREFERENCES_EMAIL, "")};
        SendEmailAsyncTask email = new SendEmailAsyncTask();

        //email.activity = this;
        email.m = new Mail(Constants.SENDER_EMAIL_USER, Constants.SENDER_EMAIL_PASS);
        email.m.set_from(Constants.SENDER_EMAIL_FROM);
        email.m.setBody(UserSession.getInstance(getContext()).getPreferences().getString(UserSession.PREFERENCES_PASS, ""));
        email.m.set_to(destinatary);
        email.m.set_subject("Meniere app, remember password");
        email.execute();
    }

    public void displayMessage(String message) {
        //Toast.makeText(this.getActivity(),message,Toast.LENGTH_LONG).show();
    }


}

class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
    Mail m;
    LoginFragment fragment = LoginFragment.newInstance();

    public SendEmailAsyncTask() {}

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            if (m.send()) {
                fragment.displayMessage("Email sent.");
            } else {
                fragment.displayMessage("Email failed to send.");
            }

            return true;
        } catch (AuthenticationFailedException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
            e.printStackTrace();
            fragment.displayMessage("Authentication failed.");
            return false;
        } catch (MessagingException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Email failed");
            e.printStackTrace();
            fragment.displayMessage("Email failed to send.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            fragment.displayMessage("Unexpected error occured.");
            return false;
        }
    }


}
