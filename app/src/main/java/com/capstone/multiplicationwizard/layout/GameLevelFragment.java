package com.capstone.multiplicationwizard.layout;


import android.app.Dialog;
import android.content.Context;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.multiplicationwizard.GameActivity;
import com.capstone.multiplicationwizard.R;
import com.capstone.multiplicationwizard.adapter.GameLevelAdapter;
import com.capstone.multiplicationwizard.data.MWSQLiteHelperNew;
import com.capstone.multiplicationwizard.fragment_interface.OnGameFragmentChangeListener;
import com.capstone.multiplicationwizard.model.Scores;
import com.capstone.multiplicationwizard.model.User;
import com.marcoscg.headerdialog.HeaderDialog;


import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 *
 */
public class GameLevelFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private GridView  mGridView = null;
    private User mCurrentUser = null;
    private GameLevelAdapter gameLevelAdapter = null;
    private OnGameFragmentChangeListener mListener;
    TextView  tv_child_name = null;
    TextView tv_child_point = null;
    MWSQLiteHelperNew helperNew;
    final ArrayList<Integer> imageItems = new ArrayList<>(12);
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GameLevelFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GameLevelFragment newInstance(int columnCount) {
        GameLevelFragment fragment = new GameLevelFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_game_level, container, false);
        mGridView = (GridView)view.findViewById(R.id.level_grid_list);
        tv_child_name = (TextView)view.findViewById(R.id.tv_levels_child_name);
        tv_child_point = (TextView)view.findViewById(R.id.tv_levels_child_point);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof OnGameFragmentChangeListener) {
            mListener = (OnGameFragmentChangeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        final GameActivity activity = (GameActivity)getActivity();
        helperNew = new MWSQLiteHelperNew(getActivity());
        mCurrentUser = activity.mCurrentUser;
        if(mCurrentUser != null) {
            tv_child_name.setText(mCurrentUser.getUsername());
            Integer score = helperNew.allScore(mCurrentUser.getUserId());
            tv_child_point.setText(score.toString());
        }
        gameLevelAdapter = new GameLevelAdapter(this.getContext(), R.layout.fragment_game_level_item, getData());
        mGridView.setAdapter(gameLevelAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if(imageItems.get(i) > 0)
                {
                mCurrentUser.setMaxLevel(i);
                Integer selectedLevel = (Integer) adapterView.getAdapter().getItem(i);


                    int titleColor;
                    String titleText = "Level ".concat(selectedLevel.toString());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        titleColor = getResources().getColor(R.color.colorAccentOrange, getActivity().getTheme());
                    } else {
                        titleColor = getResources().getColor(R.color.colorAccentOrange);
                    }
                    // custom dialog
                    final Dialog dialog = new Dialog(getActivity());
                    //dialog.setTitle(titleText);
                    dialog.setContentView(R.layout.custom_dialog_1);
                    dialog.show();

                    ((TextView) dialog.findViewById(R.id.iv_level_circle)).setText("Level " + (i + 1));
                    ((TextView) dialog.findViewById(R.id.tv_cont_str)).setText("Your Score is " + helperNew.getScore(mCurrentUser.getUserId(),""+i));


                    RatingBar iv_level_star_img = (RatingBar) dialog.findViewById(R.id.iv_level_star_img);

                    double levelscore = helperNew.getScore(mCurrentUser.getUserId(),""+i);

                    double score = ((levelscore /  MWSQLiteHelperNew.out_off) ) * 100;

                    double stars = score/20;

                    iv_level_star_img.setRating(Float.parseFloat(String.valueOf(stars)));


                    Button positiveButton = (Button) dialog.findViewById(R.id.btn_positive_txt);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();

                            mListener.OnGameFragmentChangeListener(R.id.game_level_fragment, mCurrentUser);
                        }
                    });
                    Button negativeButton = (Button) dialog.findViewById(R.id.btn_negative_txt);
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                }
                else
                {
                    Toast.makeText(activity, "Level Is Locked", Toast.LENGTH_SHORT).show();
                }
                }

            });


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // Prepare some dummy data for gridview
    private ArrayList<Integer> getData()
    {


        int maxl = helperNew.getMaxLevel(mCurrentUser.getUserId())+1;
        ArrayList<Scores>  arr_scores = new ArrayList<>();
        arr_scores = helperNew.getLevelScores(mCurrentUser.getUserId());

        if(arr_scores.size() == 0)
        {
            maxl = 0;
        }
        else
        {
            String val = arr_scores.get(arr_scores.size()-1).getLevel();
            maxl = Integer.valueOf(val)+1;
        }



        for (int i=0; i<12;i++)
        {
          //  int lscore = helperNew.getScore(mCurrentUser.getUserId(),""+(i));
            if(i <= maxl )
            {
                imageItems.add(1);
            }
            else
            {
                imageItems.add(0);
            }


               /* if(helperNew.getScore(mCurrentUser.getUserId(),""+i) > 0)
                {
                    imageItems.add(1);
                }
                else
                {
                    if(i==1)
                    {

                    }
                    else {
                        imageItems.add(0);
                    }
                }*/

        }

        return imageItems;
    }
}
