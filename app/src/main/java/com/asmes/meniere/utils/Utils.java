package com.asmes.meniere.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asmes.meniere.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author : Rajanikant
 * Date : 16 Jan 2016
 * Time : 13:08
 */
public class Utils {

    public static Toast toast;

    // Delay mechanism

    public interface DelayCallback{
        void afterDelay();
    }

    public static void delay(int secs, final DelayCallback delayCallback){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, secs * 1000); // afterDelay will be executed after (secs*1000) milliseconds.
    }

    public static void OkDialog(Context context, String title, String content){

        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(R.string.positiveTxtOk)
                .show();
    }

    public static void showToast(Context context, String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static Boolean isValidMail(String email){
        Boolean result = false;
        String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+";

        Matcher matcher = Pattern.compile(validemail).matcher(email);
        if (matcher.matches()){
            result = true;
        }

        return result;
    }


}
