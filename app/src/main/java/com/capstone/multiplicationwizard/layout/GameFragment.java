package com.capstone.multiplicationwizard.layout;

import android.app.Dialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.multiplicationwizard.BuildConfig;
import com.capstone.multiplicationwizard.GameActivity;
import com.capstone.multiplicationwizard.MultiplicationWizardAppProvider;
import com.capstone.multiplicationwizard.R;
import com.capstone.multiplicationwizard.data.MWItemsContract;
import com.capstone.multiplicationwizard.data.MWSQLiteHelperNew;
import com.capstone.multiplicationwizard.model.Scores;
import com.capstone.multiplicationwizard.model.User;
import com.capstone.multiplicationwizard.utils.RandomNumberGenerator;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;

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
    private Integer userCurrentLevelScore = 0;
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
    private User mCurrentUser = null;
    private final int levelUpScore = 80;
    private final int gameEndProblemNumber = 20;
    private InterstitialAd mInterstitialAd = null;

    MWSQLiteHelperNew helperNew;


    public GameFragment() {

        // Required empty public constructor

    }


    public void setCurrentUser(User currentUser){
        mCurrentUser = currentUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        mRootView = inflater.inflate(R.layout.fragment_game, container, false);

        context = getActivity().getApplicationContext();
        vib = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
        mediaPlayer = MediaPlayer.create(context,R.raw.clap);
        randomNumberGenerator= new RandomNumberGenerator();

        try {
            mediaPlayer.prepareAsync();
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

        currentProblemNumber = getUserProblemNumber();
        userLevel = getUserGameLevel();
        userCurrentLevelScore = getUserCurrentLevelScore();
        if (currentProblemNumber >= gameEndProblemNumber)
        {
            saveScores();
            if (userCurrentLevelScore > levelUpScore)
            {
                if(mCurrentUser.getMaxLevel() >= (MWItemsContract.GAMELEVEL - 1)) {
                    showGameCompleteDialog();
                }
                else {
                    showLevelUpDialog();
                }
            }
            else {
                showContinueDialog();
            }
        } else {
            TextView textViewP1 = (TextView) mRootView.findViewById(R.id.tv_p1);
            TextView textViewP2 = (TextView) mRootView.findViewById(R.id.tv_p2);

            problemList = randomNumberGenerator.getMultiplicationPairs(userLevel.first, userLevel.second);

            Integer p1 = problemList.get(currentProblemNumber).first;
            Integer p2 = problemList.get(currentProblemNumber).second;
            textViewP1.setText(p1.toString());
            textViewP2.setText(p2.toString());
            currentProblemAnswer = p1 * p2;
            currentAnswerSlot = randomNumberGenerator.getRandomNumberTillValue(5)-1;
            loadAnswerWidgets();
        }
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
            updateUserCurrentLevelScore();
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
        return currentProblemNumber;
    }
    // Based on the user level we want to get the left multiple and right multiple
    private Pair<Integer,Integer> getUserGameLevel() {
        Integer x,y;
        switch(mCurrentUser.getMaxLevel()) {

            case 0:
                x = 3;
                y = 4;
                break;

            case 1:
                x = 4;
                y = 5;
                break;
            case 2:
                x = 5;
                y = 6;
                break;

            case 3:
                x = 6;
                y = 7;
                break;
            case 4:
                x = 7;
                y = 8;
                break;

            case 5:
                x = 8;
                y = 9;
                break;

            case 6:
                x = 9;
                y = 10;
                break;

            case 7:
                x = 10;
                y = 11;
                break;

            case 8:
                x = 11;
                y = 12;
                break;

            case 9:
                x = 12;
                y = 13;
                break;

            case 10:
                x = 13;
                y = 14;
                break;

            case 11:
                x = 14;
                y = 15;
                break;
            default:
                x = 14;
                y = 17;


        }
        Pair<Integer,Integer> pair = new Pair<Integer,Integer>(x,y);
        return pair;
    }
    private Integer getUserCurrentLevelScore() {
        return userCurrentLevelScore;
    }
    private void updateUserCurrentLevelScore() {
        userCurrentLevelScore = userCurrentLevelScore +5;
    }

    private void saveScores() {

        helperNew = new MWSQLiteHelperNew(getActivity());
        new SaveScoreAsyncTask(helperNew,mCurrentUser,getUserCurrentLevelScore()).execute(null,null,null);

    }
    private void showLevelUpDialog(){

        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog_level_up);
        dialog.setCancelable(false);
        Button positiveButton = (Button)dialog.findViewById(R.id.btn_positive_txt);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if(BuildConfig.FLAVOR.equals("demo")) {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            Intent intent = new Intent(getActivity().getApplicationContext(), GameActivity.class);
                            intent.putExtra("com.capstone.multiplicationwizard.model.user", mCurrentUser);
                            startActivity(intent);

                        }
                    });
                }
                else {
                    Intent intent = new Intent(getActivity().getApplicationContext(), GameActivity.class);
                    intent.putExtra("com.capstone.multiplicationwizard.model.user", mCurrentUser);
                    startActivity(intent);
                    getActivity().onBackPressed();
                }

            }
        });
        dialog.show();
    }

    private void showContinueDialog(){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Failed");
        builder.setCancelable(false);
        builder.setTitle("Sorry you failed the level.  Try Again");
        builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity().getApplicationContext(), GameActivity.class);
                intent.putExtra("com.capstone.multiplicationwizard.model.user",mCurrentUser);
                startActivity(intent);
                getActivity().onBackPressed();
            }
        });
        builder.create().show();

    }
    private void showGameCompleteDialog(){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Congratulataions.Keep working hard !");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity().getApplicationContext(), GameActivity.class);
                intent.putExtra("com.capstone.multiplicationwizard.model.user",mCurrentUser);
                startActivity(intent);
                getActivity().onBackPressed();
            }
        });
        builder.create().show();

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
    class SaveScoreAsyncTask extends AsyncTask<Void,Void,Void> {

        private MWSQLiteHelperNew helperNew;
        private User userInfo;
        private Integer currLevScore;

        public SaveScoreAsyncTask(MWSQLiteHelperNew db, User user,Integer userCurrentLevelScore) {
            helperNew = db;
            userInfo = user;
            currLevScore = userCurrentLevelScore;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);

            AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
            ComponentName widgetComponent = new ComponentName(context,MultiplicationWizardAppProvider.class);
            int[] widgetIds = widgetManager.getAppWidgetIds(widgetComponent);
            Intent update = new Intent();
            update.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);
            update.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            context.sendBroadcast(update);
            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);

            Bundle params = new Bundle();
            params.putString("user name", userInfo.getUsername());
            params.putString("user level", userInfo.getMaxLevel().toString());
            firebaseAnalytics.logEvent("share_image", params);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if(helperNew.isPlayedLevel(userInfo.getUserId(),""+userInfo.getMaxLevel()))
            {
                if(helperNew.getScore(userInfo.getUserId(),""+userInfo.getMaxLevel()) < currLevScore)
                {
                    helperNew.updateScore(new Scores(userInfo.getUserId(), "" + userInfo.getMaxLevel(), "" + currLevScore));
                }
            }
            else
            {
                helperNew.addScore(new Scores(userInfo.getUserId(), "" + userInfo.getMaxLevel(), "" + currLevScore));
            }
            return null;
        }
    }
}
