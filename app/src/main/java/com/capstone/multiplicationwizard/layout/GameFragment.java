package com.capstone.multiplicationwizard.layout;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capstone.multiplicationwizard.GameActivity;
import com.capstone.multiplicationwizard.R;
import com.capstone.multiplicationwizard.utils.RandomNumberGenerator;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {
    private ArrayList<Pair<Integer,Integer>> problemList;
    private Pair<Integer,Integer>userLevel;
    private Integer userScoreLevel = 0;
    private Integer currentProblemNumber = 0;
    private Integer currentProblemAnswer = -1;
    private Integer currentAnswerSlot = 0;
    private Vibrator vib;
    private Context context;
    private MediaPlayer mediaPlayer;
    private static final String TAG = GameActivity.class.getName();
    private RandomNumberGenerator randomNumberGenerator;
    private OnFragmentInteractionListener mListener;
    private View mRootView = null;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mRootView = inflater.inflate(R.layout.fragment_game, container, false);

        context = getActivity().getApplicationContext();
        vib = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);

        mediaPlayer = MediaPlayer.create(context,R.raw.clap);
        randomNumberGenerator= new RandomNumberGenerator();

        try {
            mediaPlayer.prepare();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return mRootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWidgets();
    }
    private void loadWidgets() {
        TextView textViewP1 = (TextView)mRootView.findViewById(R.id.tv_p1);
        TextView textViewP2 = (TextView)mRootView.findViewById(R.id.tv_p2);
        userLevel = getUserGameLevel();
        userScoreLevel = getUserScoreLevel();
        problemList = randomNumberGenerator.getMultiplicationPairs(userLevel.first,userLevel.second);
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
        TextView textViewS1 = (TextView)mRootView.findViewById(R.id.tv_s1);
        TextView textViewS2 = (TextView)mRootView.findViewById(R.id.tv_s2);
        TextView textViewS3 = (TextView)mRootView.findViewById(R.id.tv_s3);
        TextView textViewS4 = (TextView)mRootView.findViewById(R.id.tv_s4);

        textViewS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerSlotClickListener(view);
            }
        });
        textViewS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerSlotClickListener(view);
            }
        });
        textViewS3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerSlotClickListener(view);
            }
        });
        textViewS4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerSlotClickListener(view);
            }
        });

        if(currentAnswerSlot  == 0) {
            textViewS1.setText(currentProblemAnswer.toString());
            tempValue = currentProblemAnswer + randomNumberGenerator.getRandomNumberTillValue(5);
            textViewS2.setText(tempValue.toString());
            tempValue = currentProblemAnswer + randomNumberGenerator.getRandomNumberTillValue(5);
            textViewS3.setText(tempValue.toString());
            tempValue = currentProblemAnswer + randomNumberGenerator.getRandomNumberTillValue(5);
            textViewS4.setText(tempValue.toString());
        }
        else if(currentAnswerSlot  == 1) {
            textViewS2.setText(currentProblemAnswer.toString());
            tempValue = currentProblemAnswer + randomNumberGenerator.getRandomNumberTillValue(5);
            textViewS1.setText(tempValue.toString());
            tempValue = currentProblemAnswer + randomNumberGenerator.getRandomNumberTillValue(5);
            textViewS3.setText(tempValue.toString());
            tempValue = currentProblemAnswer + randomNumberGenerator.getRandomNumberTillValue(5);
            textViewS4.setText(tempValue.toString());
        }
        if(currentAnswerSlot  == 2) {
            textViewS3.setText(currentProblemAnswer.toString());
            tempValue = currentProblemAnswer + randomNumberGenerator.getRandomNumberTillValue(5);
            textViewS1.setText(tempValue.toString());
            tempValue = currentProblemAnswer + randomNumberGenerator.getRandomNumberTillValue(5);
            textViewS2.setText(tempValue.toString());
            tempValue = currentProblemAnswer + randomNumberGenerator.getRandomNumberTillValue(5);
            textViewS4.setText(tempValue.toString());
        }
        if(currentAnswerSlot  == 3) {
            textViewS4.setText(currentProblemAnswer.toString());
            tempValue = currentProblemAnswer + randomNumberGenerator.getRandomNumberTillValue(5);
            textViewS1.setText(tempValue.toString());
            tempValue = currentProblemAnswer + randomNumberGenerator.getRandomNumberTillValue(5);
            textViewS2.setText(tempValue.toString());
            tempValue = currentProblemAnswer + randomNumberGenerator.getRandomNumberTillValue(5);
            textViewS3.setText(tempValue.toString());
        }

    }
    public void  answerSlotClickListener(View view) {

        TextView txtView = (TextView)view;

        if(txtView.getText().toString().equals(currentProblemAnswer.toString())) {
            mediaPlayer.start();
            updateUserScoreLevel();
        }
        else {
            //Vibrate
            vib.vibrate(100);
        }
        incrementUserProblemNumber();
        loadWidgets();
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
    private Integer getUserScoreLevel() {
        return 0;
    }
    private void updateUserScoreLevel() {
        userScoreLevel++;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
