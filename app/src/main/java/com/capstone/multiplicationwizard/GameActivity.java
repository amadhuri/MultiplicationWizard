package com.capstone.multiplicationwizard;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import com.capstone.multiplicationwizard.utils.RandomNumberGenerator;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private Fragment mCurrentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //mCurrentFragment = getCurrentFragment();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
