package com.capstone.multiplicationwizard;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.capstone.multiplicationwizard.data.MWItemsContract;
import com.capstone.multiplicationwizard.data.MWSQLiteHelperNew;
import com.capstone.multiplicationwizard.layout.NewUserFragment;
import com.capstone.multiplicationwizard.layout.UsersFragment;
import com.morsebyte.shailesh.twostagerating.TwoStageRate;


public class MainActivity extends AppCompatActivity implements UsersFragment.OnUsersFragmentAddNewUserListener {

    private Fragment mCurrentFragment = null;
    private ContentResolver mContentResolver = null;
    public MWSQLiteHelperNew mwDB = null;
    private static final int SPLASH_SCREEN_DELAY = 100;
    private static final String TAG = MainActivity.class.getName();
    TwoStageRate twoStageRate;


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MainActivity", "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.e("MainActivity", "OnPause called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MainActivity", "onDestroy called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MainActivity", "onStart called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MainActivity", "onStop called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCurrentFragment = getCurrentFragment();
        mContentResolver = getContentResolver();

        twoStageRate = TwoStageRate.with(this);
        twoStageRate.settings.setLaunchTimes(100);
        twoStageRate.resetOnDismiss(true).resetOnFeedBackDeclined(true).resetOnRatingDeclined(false);
        twoStageRate.showIfMeetsConditions();

        Cursor cursor = mContentResolver.query(MWItemsContract.USERS_CONTENT_URI,
                null, null, null, null, null);
        final int userCount = cursor.getCount();
        Log.e("MainActivity", "onCreate Usercount:" + userCount);
        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                detachCurrentFragment();
                if (userCount > 0)
                    attachUsersFragment();
                else
                    attachNewUserFragment();

            }
        }, SPLASH_SCREEN_DELAY);

    }

    private int getUserCount() {
        int userCount = 0;
        Cursor cursor = mContentResolver.query(MWItemsContract.USERS_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            Log.e("Word Count", cursor.getCount() + "");
            userCount = cursor.getCount();
            cursor.close();
        }
        return userCount;
    }

    public Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager.findFragmentById(R.id.splash_fragment);
    }

    private void detachCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(mCurrentFragment);
    }

    private void attachNewUserFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_up, 0, 0, 0);
        NewUserFragment fragment = new NewUserFragment();
        fragmentTransaction.add(R.id.fragmentParentViewGroup, fragment);
        fragmentTransaction.commitAllowingStateLoss();
        ;
        mCurrentFragment = (Fragment) fragment;
        return;
    }

    private void attachUsersFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_up, 0, 0, 0);
        UsersFragment fragment = new UsersFragment();
        fragmentTransaction.add(R.id.fragmentParentViewGroup, fragment);
        fragmentTransaction.commitAllowingStateLoss();
        mCurrentFragment = (Fragment) fragment;
        return;
    }

    public void onFragmentAddNewUser() {
        detachCurrentFragment();
        attachNewUserFragment();
    }

}
