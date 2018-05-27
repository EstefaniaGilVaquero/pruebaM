package com.asmes.meniere.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.TextView;

import com.asmes.meniere.R;
import com.asmes.meniere.activity.Login.LoginFingerTipFragment;
import com.asmes.meniere.adapter.DatabaseHelper;
import com.asmes.meniere.activity.HelpMeniere.FourFragment;
import com.asmes.meniere.activity.MyMeniere.OneFragment;
import com.asmes.meniere.activity.UtilitiesMeniere.ThreeFragment;
import com.asmes.meniere.activity.AboutMeniere.TwoFragment;
import com.asmes.meniere.prefs.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TabsActivity extends AppCompatActivity {
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static boolean activitySwitchFlag = false;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Activity activity;
    private int currentPage = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            setContentView(R.layout.activity_tabs);

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            activity = this;

            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            viewPager =  findViewById(R.id.viewpager);
            tabLayout =  findViewById(R.id.tabs);

            dbHelper = new DatabaseHelper(this);
            db = dbHelper.getWritableDatabase();

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

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
        if(currentPage == 0) {
            Fragment fragment = new OneFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.oneFragmentFrame, fragment);
            transaction.commit();
        }
        UserSession.getInstance(activity).setmIsLoggedIn(false);
    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(R.string.MyMeniere);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_mymeniere, 0, 0);
        else tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_mymeniere1, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(R.string.aboutMeniere);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_about, 0, 0);
        else tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_about1, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(R.string.utilities);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_utilidades, 0, 0);
        else tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.utilidades1, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText(R.string.help);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_help, 0, 0);
        else tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_help1, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }

    /**
     * Adding fragments to ViewPager
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            TabsActivity.ViewPagerAdapter adapter = new TabsActivity.ViewPagerAdapter(fragmentManager);
            adapter.addFrag(new OneFragment(), "ROOT_FRAGMENT");
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
