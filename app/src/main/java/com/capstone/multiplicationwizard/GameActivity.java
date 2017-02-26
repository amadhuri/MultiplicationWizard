package com.capstone.multiplicationwizard;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.capstone.multiplicationwizard.fragment_interface.OnGameFragmentChangeListener;
import com.capstone.multiplicationwizard.layout.GameFragment;
import com.capstone.multiplicationwizard.layout.GameLevelFragment;
import com.capstone.multiplicationwizard.layout.NewUserFragment;
import com.capstone.multiplicationwizard.model.User;
import com.capstone.multiplicationwizard.utils.RandomNumberGenerator;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements OnGameFragmentChangeListener{
    private Fragment mCurrentFragment = null;
    public User mCurrentUser = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle userBundle = getIntent().getExtras();
        mCurrentUser = userBundle.getParcelable("com.capstone.multiplicationwizard.model.user");
        //mCurrentFragment = getCurrentFragment();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void OnGameFragmentChangeListener(int curFragment, User curUser) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        mCurrentFragment = fragmentManager.findFragmentById(curFragment);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(mCurrentFragment);
        fragmentTransaction.setCustomAnimations(R.anim.slide_up,0,0, 0);
        if (curFragment == R.id.game_level_fragment) {
            //Navigate from game level fragment to Game fragment
            GameFragment newFragment = new GameFragment();;
            fragmentTransaction.add(R.id.fragmentParentViewGroup, newFragment);
            mCurrentFragment = (Fragment)newFragment;
        } else {
            //Navigate from Game fragment to game level fragment
            GameLevelFragment newFragment = new GameLevelFragment();
            fragmentTransaction.add(R.id.fragmentParentViewGroup, newFragment);
            mCurrentFragment = (Fragment)newFragment;
        }
        fragmentTransaction.commit();
        return;
    }
 }

