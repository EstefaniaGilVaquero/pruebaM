package com.asmes.meniere.activity.Login;

import android.os.AsyncTask;
import android.util.Log;

import com.asmes.meniere.models.Mail;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

public class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
    Mail m;
    LoginFingerTipFragment fragment = LoginFingerTipFragment.newInstance();

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
