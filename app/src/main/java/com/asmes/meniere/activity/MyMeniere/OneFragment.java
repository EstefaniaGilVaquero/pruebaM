package com.asmes.meniere.activity.MyMeniere;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asmes.meniere.R;
import com.asmes.meniere.activity.Login.LoginFingerTipFragment;
import com.asmes.meniere.activity.Login.RegisterFragment;
import com.asmes.meniere.prefs.UserSession;

public class OneFragment extends Fragment {

    private View rootView;

    public OneFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_one, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (UserSession.getInstance(getContext()).ismIsLoggedIn()){
            Fragment fragment = new MyMeniereFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.oneFragmentFrame, fragment);
            transaction.commit();
        }
        //Si no hay password vamos al registro
        else if(UserSession.getInstance(getContext()).getPreferences().getString(UserSession.PREFERENCES_PASS, "").equals("")){
            Fragment fragment = RegisterFragment.newInstance();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.oneFragmentFrame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }//Si no está logueado voy a login
        else if(!UserSession.getInstance(getContext()).ismIsLoggedIn()){
            Fragment fragment = LoginFingerTipFragment.newInstance();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.oneFragmentFrame, fragment);
            transaction.commit();
        }
    }
}
