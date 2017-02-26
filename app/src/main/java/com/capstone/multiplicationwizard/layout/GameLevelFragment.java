package com.capstone.multiplicationwizard.layout;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
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
import android.widget.TextView;

import com.capstone.multiplicationwizard.GameActivity;
import com.capstone.multiplicationwizard.R;
import com.capstone.multiplicationwizard.adapter.GameLevelAdapter;
import com.capstone.multiplicationwizard.fragment_interface.OnGameFragmentChangeListener;
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

    private GameLevelAdapter gameLevelAdapter = null;
    private User mCurrentUser = null;
    private OnGameFragmentChangeListener mListener;
    TextView  tv_child_name = null;
    TextView tv_child_point = null;

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
        GameActivity activity = (GameActivity)getActivity();
        mCurrentUser = activity.mCurrentUser;
        if(mCurrentUser != null) {
            tv_child_name.setText(mCurrentUser.getUsername());
            tv_child_point.setText(mCurrentUser.getScore().toString());
        }
        gameLevelAdapter = new GameLevelAdapter(this.getContext(), R.layout.fragment_game_level_item, getData());
        mGridView.setAdapter(gameLevelAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Integer selectedLevel = (Integer)adapterView.getAdapter().getItem(i);
                int titleColor;
                String titleText = "Level ".concat(selectedLevel.toString());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    titleColor = getResources().getColor(R.color.colorAccentOrange, getActivity().getTheme());
                }
                else {
                    titleColor = getResources().getColor(R.color.colorAccentOrange);
                }
                // custom dialog
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_dialog_1);
                dialog.show();

                Button positiveButton = (Button)dialog.findViewById(R.id.btn_positive_txt);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        mListener.OnGameFragmentChangeListener(R.id.game_level_fragment,mCurrentUser );
                    }
                });
                Button negativeButton = (Button)dialog.findViewById(R.id.btn_negative_txt);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                /*new HeaderDialog(getActivity())
                            .setColor(titleColor) // Sets the header background color
                            .setElevation(false) // Sets the header elevation, true by default
                            .setTitle(titleText) // Sets the dialog title
                            .setMessage("Lorem ipsum dolor sit amet...") // Sets the dialog message
                            .justifyContent(true) // Justifies the message text, false by default
                            .setTitleColor(Color.parseColor("#FFFFFF")) // Sets the title text color
                            .setTitleGravity(Gravity.CENTER_HORIZONTAL) // Sets the title text gravity
                            .setPositiveButton("START", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mListener.OnGameFragmentChangeListener(R.id.game_level_fragment,mCurrentUser );
                                }
                            })
                            .setNegativeButton("BACK", null)
                            .show();*/
                }
            });

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // Prepare some dummy data for gridview
    private ArrayList<Integer> getData() {
        final ArrayList<Integer> imageItems = new ArrayList<>(12);
        int curLevel = mCurrentUser.getLevel();
        for (int i=1; i<=curLevel;i++)
            imageItems.add(i);
        for (int i = curLevel+1; i<=12; i++)
            imageItems.add(0);

        return imageItems;
    }
}
