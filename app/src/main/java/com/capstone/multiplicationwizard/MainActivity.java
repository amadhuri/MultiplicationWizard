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
import com.capstone.multiplicationwizard.data.MWSQLiteHelper;
import com.capstone.multiplicationwizard.data.MWSQLiteHelperNew;
import com.capstone.multiplicationwizard.layout.NewUserFragment;
import com.capstone.multiplicationwizard.layout.UsersFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity implements UsersFragment.OnUsersFragmentAddNewUserListener {

    private Fragment mCurrentFragment = null;
    private ContentResolver mContentResolver = null;
    public MWSQLiteHelperNew mwDB = null;
    private static final int SPLASH_SCREEN_DELAY = 100;
    private InterstitialAd mInterstitialAd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCurrentFragment = getCurrentFragment();
        mContentResolver = getContentResolver();
        mwDB = new MWSQLiteHelperNew(getApplicationContext());

        final int userCount = mwDB.getUsers().size();
        Log.e("MainActivity", "Usercount:"+userCount);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                detachCurrentFragment();
                if(userCount > 0)
                    attachUsersFragment();
                else
                    attachNewUserFragment();
            }
        });
        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }

            }
        },SPLASH_SCREEN_DELAY);
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
        fragmentTransaction.setCustomAnimations(R.anim.slide_up,0,0, 0);
        //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        NewUserFragment fragment = new NewUserFragment();
        fragmentTransaction.add(R.id.fragmentParentViewGroup, fragment);
        fragmentTransaction.commitAllowingStateLoss();;
        mCurrentFragment = (Fragment)fragment;
        return;
    }

    private void attachUsersFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_up,0,0, 0);
        //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        UsersFragment fragment = new UsersFragment();
        fragmentTransaction.add(R.id.fragmentParentViewGroup, fragment);
        fragmentTransaction.commitAllowingStateLoss();
        mCurrentFragment = (Fragment)fragment;
        return;
    }

    public void onFragmentAddNewUser() {
        detachCurrentFragment();
        attachNewUserFragment();
    }

}
