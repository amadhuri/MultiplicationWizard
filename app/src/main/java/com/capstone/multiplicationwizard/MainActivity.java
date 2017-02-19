package com.capstone.multiplicationwizard;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.capstone.multiplicationwizard.data.MWItemsContract;
import com.capstone.multiplicationwizard.data.MWSQLiteHelper;
import com.capstone.multiplicationwizard.layout.NewUserFragment;
import com.capstone.multiplicationwizard.layout.SplashFragment;
import com.capstone.multiplicationwizard.layout.UsersFragment;

public class MainActivity extends AppCompatActivity implements UsersFragment.OnUsersFragmentAddNewUserListener {

    private Fragment mCurrentFragment = null;
    private ContentResolver mContentResolver = null;
    public MWSQLiteHelper mwDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*currentFragment = attachSplashFragment();*/
        mCurrentFragment = getCurrentFragment();
        mContentResolver = getContentResolver();
        mwDB = new MWSQLiteHelper(getApplicationContext());
        //final int userCount = mwDB.getUserCount();
        final int userCount = getUserCount();
        Log.e("MainActivity", "Usercount:"+userCount);
        Thread timer= new Thread()
        {
            public void run()
            {
                try
                {
                    //Display for 1 seconds
                    sleep(1000);
                }
                catch (InterruptedException e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally
                {
                    detachCurrentFragment();
                    if(userCount > 0)
                        attachUsersFragment();
                    else
                        attachNewUserFragment();
                }
            }
        };
        timer.start();
    }

    private int getUserCount() {
        Cursor cursor = mContentResolver.query(MWItemsContract.USERS_CONTENT_URI, null, null, null, null);
        Log.e("Word Count", cursor.getCount() + "");
        int userCount = cursor.getCount();
        cursor.close();
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
        fragmentTransaction.commit();
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
        fragmentTransaction.commit();
        mCurrentFragment = (Fragment)fragment;
        return;
    }

    public void onFragmentAddNewUser() {
        detachCurrentFragment();
        attachNewUserFragment();
    }

}
