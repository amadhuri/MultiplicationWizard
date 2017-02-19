package com.capstone.multiplicationwizard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.capstone.multiplicationwizard.utils.AudioClip;
import com.capstone.multiplicationwizard.utils.RandomNumberGenerator;

import java.util.ArrayList;

import static com.capstone.multiplicationwizard.utils.AudioClip.clapSoundID;

public class GameActivity extends AppCompatActivity {

    private ArrayList<Pair<Integer,Integer>> problemList;
    private Pair<Integer,Integer>userLevel;
    private Integer currentProblemNumber = 0;
    private Integer currentProblemAnswer = -1;
    private Integer currentAnswerSlot = 0;
    private AudioClip audioClip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioClip = AudioClip.getInstance(getApplicationContext());
        setContentView(R.layout.activity_game);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWidgets();

    }
    private void loadWidgets() {
        TextView textViewP1 = (TextView)findViewById(R.id.tv_p1);
        TextView textViewP2 = (TextView)findViewById(R.id.tv_p2);
        userLevel = getUserGameLevel();
        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator(userLevel.first,userLevel.second);
        problemList = randomNumberGenerator.getMultiplicationPairs();
        currentProblemNumber = getUserProblemNumber();
        Integer p1 = problemList.get(currentProblemNumber).first;
        Integer p2 = problemList.get(currentProblemNumber).second;
        textViewP1.setText(p1.toString());
        textViewP2.setText(p2.toString());
        currentProblemAnswer = p1*p2;
        currentAnswerSlot = randomNumberGenerator.getRandomNumberTillValue(3);
        loadAnswerWidgets();
    }
    private void loadAnswerWidgets() {
        Integer tempValue;
        TextView textViewS1 = (TextView)findViewById(R.id.tv_s1);
        TextView textViewS2 = (TextView)findViewById(R.id.tv_s2);
        TextView textViewS3 = (TextView)findViewById(R.id.tv_s3);
        TextView textViewS4 = (TextView)findViewById(R.id.tv_s4);
        if(currentAnswerSlot  == 0) {
            textViewS1.setText(currentProblemAnswer.toString());
            tempValue = currentProblemAnswer + 2;
            textViewS2.setText(tempValue.toString());
            tempValue = currentProblemAnswer + 4;
            textViewS3.setText(tempValue.toString());
            tempValue = currentProblemAnswer + 6;
            textViewS4.setText(tempValue.toString());
        }
        else if(currentAnswerSlot  == 1) {
            textViewS2.setText(currentProblemAnswer.toString());
            tempValue = currentProblemAnswer + 2;
            textViewS1.setText(tempValue.toString());
            tempValue = currentProblemAnswer + 4;
            textViewS3.setText(tempValue.toString());
            tempValue = currentProblemAnswer + 6;
            textViewS4.setText(tempValue.toString());
        }
        if(currentAnswerSlot  == 2) {
            textViewS3.setText(currentProblemAnswer.toString());
            tempValue = currentProblemAnswer + 2;
            textViewS1.setText(tempValue.toString());
            tempValue = currentProblemAnswer + 4;
            textViewS2.setText(tempValue.toString());
            tempValue = currentProblemAnswer + 6;
            textViewS4.setText(tempValue.toString());
        }
        if(currentAnswerSlot  == 3) {
            textViewS4.setText(currentProblemAnswer.toString());
            tempValue = currentProblemAnswer + 2;
            textViewS1.setText(tempValue.toString());
            tempValue = currentProblemAnswer + 4;
            textViewS2.setText(tempValue.toString());
            tempValue = currentProblemAnswer + 6;
            textViewS3.setText(tempValue.toString());
        }

    }
    public void  answerSlotClickListener(View view) {

        TextView txtView = (TextView)view;

       // if(txtView.getText().toString().equals(currentAnswerSlot.toString())) {

            audioClip.playSound(audioClip.clapSoundID(),5,5);
            incrementUserProblemNumber();
            loadWidgets();
            audioClip.stopSound(audioClip.clapSoundID());
        //}
        //else {
             //Vibrate
        //}

    }
    private void incrementUserProblemNumber() {
        currentProblemNumber++;
    }
    private int getUserProblemNumber() {
        return currentProblemNumber >= 20 ? 0: currentProblemNumber++;
    }
    // Based on the user level we want to get the left multiple and right multiple
    private Pair<Integer,Integer> getUserGameLevel() {
        Integer x =3;
        Integer y =9;
        Pair<Integer,Integer> pair = new Pair<Integer,Integer>(x,y);
        return pair;
    }
}
