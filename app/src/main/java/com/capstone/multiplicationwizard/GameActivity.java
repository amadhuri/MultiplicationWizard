package com.capstone.multiplicationwizard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import com.capstone.multiplicationwizard.utils.RandomNumberGenerator;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    ArrayList<Pair<Integer,Integer>> problemList;
    Pair<Integer,Integer>userLevel;
    int problemNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        problemNumber = getUserProblemNumber();
        Integer p1 = problemList.get(problemNumber).first;
        Integer p2 = problemList.get(problemNumber).second;
        textViewP1.setText(p1.toString());
        textViewP2.setText(p2.toString());
        Integer answer = p1*p2;
        Integer answerSlot = randomNumberGenerator.getRandomNumberTillValue(3);
        loadAnswerWidgets(answer,answerSlot);
    }
    private void loadAnswerWidgets(Integer answer,Integer answerSlot) {
        Integer tempValue;
        TextView textViewS1 = (TextView)findViewById(R.id.tv_s1);
        TextView textViewS2 = (TextView)findViewById(R.id.tv_s2);
        TextView textViewS3 = (TextView)findViewById(R.id.tv_s3);
        TextView textViewS4 = (TextView)findViewById(R.id.tv_s4);
        if(answerSlot  == 0) {
            textViewS1.setText(answer.toString());
            tempValue = answer + 2;
            textViewS2.setText(tempValue.toString());
            tempValue = answer + 4;
            textViewS3.setText(tempValue.toString());
            tempValue = answer + 6;
            textViewS4.setText(tempValue.toString());
        }
        else if(answerSlot  == 1) {
            textViewS2.setText(answer.toString());
            tempValue = answer + 2;
            textViewS1.setText(tempValue.toString());
            tempValue = answer + 4;
            textViewS3.setText(tempValue.toString());
            tempValue = answer + 6;
            textViewS4.setText(tempValue.toString());
        }
        if(answerSlot  == 2) {
            textViewS3.setText(answer.toString());
            tempValue = answer + 2;
            textViewS1.setText(tempValue.toString());
            tempValue = answer + 4;
            textViewS2.setText(tempValue.toString());
            tempValue = answer + 6;
            textViewS4.setText(tempValue.toString());
        }
        if(answerSlot  == 3) {
            textViewS4.setText(answer.toString());
            tempValue = answer + 2;
            textViewS1.setText(tempValue.toString());
            tempValue = answer + 4;
            textViewS2.setText(tempValue.toString());
            tempValue = answer + 6;
            textViewS3.setText(tempValue.toString());
        }

    }
    private int getUserProblemNumber() {
        return problemNumber >= 20 ? 0: problemNumber++;
    }
    // Based on the user level we want to get the left multiple and right multiple
    private Pair<Integer,Integer> getUserGameLevel() {
        Integer x =3;
        Integer y =9;
        Pair<Integer,Integer> pair = new Pair<Integer,Integer>(x,y);
        return pair;
    }
}
