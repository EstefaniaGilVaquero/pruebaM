package com.asmes.meniere.activity;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.TextView;

import com.asmes.meniere.R;
import com.asmes.meniere.adapter.DatabaseHelper;
import com.asmes.meniere.activity.HelpMeniere.FourFragment;
import com.asmes.meniere.activity.MyMeniere.OneFragment;
import com.asmes.meniere.activity.UtilitiesMeniere.ThreeFragment;
import com.asmes.meniere.activity.AboutMeniere.TwoFragment;
import com.asmes.meniere.prefs.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TabsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static boolean activitySwitchFlag = false;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            setContentView(R.layout.activity_tabs);

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            viewPager =  findViewById(R.id.viewpager);
            tabLayout =  findViewById(R.id.tabs);

            dbHelper = new DatabaseHelper(this);
            db = dbHelper.getWritableDatabase();

            setupViewPager(viewPager);
            if (tabLayout != null) {
                tabLayout.setupWithViewPager(viewPager);
            }
            setupTabIcons();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause(){
        super.onPause();

        if (!activitySwitchFlag) {
            // Cambiamos de activity y no hacemos nada
            // Hemos pulsado home, matamos la app
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            finishAffinity();
        }else{
            UserSession.getInstance(this).setmIsLoggedIn(false);
        }
        activitySwitchFlag = false;
    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(R.string.MyMeniere);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_mymeniere, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(R.string.aboutMeniere);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_about, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(R.string.utilities);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_utilidades, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText(R.string.help);
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_help, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }

    /**
     * Adding fragments to ViewPager
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        try {
            TabsActivity.ViewPagerAdapter adapter = new TabsActivity.ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFrag(new OneFragment(), "");
            adapter.addFrag(new TwoFragment(), "");
            adapter.addFrag(new ThreeFragment(), "");
            adapter.addFrag(new FourFragment(), "");
            viewPager.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
