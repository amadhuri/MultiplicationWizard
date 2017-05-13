package com.capstone.multiplicationwizard.layout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.capstone.multiplicationwizard.GameActivity;
import com.capstone.multiplicationwizard.R;
import com.capstone.multiplicationwizard.adapter.UsersAdapter;
import com.capstone.multiplicationwizard.data.MWItemsContract;
import com.capstone.multiplicationwizard.data.MWSQLiteHelper;
import com.capstone.multiplicationwizard.data.MWSQLiteHelperNew;
import com.capstone.multiplicationwizard.model.User;

import java.util.ArrayList;

public class UsersFragment extends Fragment {

    View mRootView = null;
    Cursor mCursor = null;

    private OnUsersFragmentAddNewUserListener mListener = null;
    MWSQLiteHelperNew helperNew;
    public UsersFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setUserAdapterItems(UsersAdapter adapter)
    {

        ArrayList<User> arr_users = new ArrayList<>();
        arr_users = helperNew.getUsers();

        for (User u:arr_users)
        {
            adapter.add(u);
        }

       /* Cursor cursor = getActivity().getContentResolver().query(MWItemsContract.USERS_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String userName = cursor.getString(cursor.getColumnIndex(MWSQLiteHelper.KEY_USERNAME));
                int level = cursor.getInt(cursor.getColumnIndex(MWSQLiteHelper.KEY_LEVEL));
                int score = cursor.getInt(cursor.getColumnIndex(MWSQLiteHelper.KEY_HIGHSCORE));
                String user_id = cursor.getString(cursor.getColumnIndex(MWSQLiteHelper.KEY_ID));
                User newUser = new User();
                setUserLevelScores(user_id, newUser);
                newUser.setUsername(userName);
                newUser.setHighScore(score);
                newUser.setMaxLevel(level);
                newUser.setId(user_id);
                adapter.add(newUser);
                cursor.moveToNext();
            }
            cursor.close();
        }
        */
    }

    private void setUserLevelScores(String userId, User newUser) {

        String mSelectionClause = MWSQLiteHelper.KEY_ID + " = ?";
        String[] mSelectionArgs = {""};
        mSelectionArgs[0] = userId;
        Cursor cursor = getActivity().getContentResolver().query(MWItemsContract.USER_LEVEL_CONTENT_URI,null,mSelectionClause,mSelectionArgs,null);
        if (cursor != null) {
            int count = cursor.getCount();
            newUser.levelScores = new ArrayList<User.LevelScores>(count);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int level = cursor.getInt(cursor.getColumnIndex(MWSQLiteHelper.KEY_LEVEL));
                int score = cursor.getInt(cursor.getColumnIndex(MWSQLiteHelper.KEY_HIGHSCORE));
            }

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayList<User> arrayOfUsers = new ArrayList<User>();

        UsersAdapter adapter = new UsersAdapter(this.getContext(), arrayOfUsers);
        /*for(String name:mobileArray) {
            User newUser = new User(name);
            adapter.add(newUser);
        }*/
        helperNew = new MWSQLiteHelperNew(getActivity());
        setUserAdapterItems(adapter);
        mRootView = inflater.inflate(R.layout.fragment_users, container, false);

        final ListView childListView = (ListView) mRootView.findViewById(R.id.childLV);

        childListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User currentUser = (User)adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(getActivity().getApplicationContext(), GameActivity.class);
                intent.putExtra("com.capstone.multiplicationwizard.model.user",currentUser);
                startActivity(intent);
                return;

            }
        });

        childListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                User currentUser = (User)adapterView.getAdapter().getItem(i);

                int del = helperNew.deleteUser(currentUser.getUserId());
                if(del > 0) {
                    ArrayList<User> arrayOfUsers = new ArrayList<User>();
                    UsersAdapter adapter = new UsersAdapter(getActivity(), arrayOfUsers);
                    setUserAdapterItems(adapter);
                    childListView.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(getActivity(), "User not Deleted", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        childListView.setAdapter(adapter);

        FloatingActionButton addFab = (FloatingActionButton) mRootView.findViewById(R.id.add_users_fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(helperNew.getUsers().size() < 5)
                {
                    mListener.onFragmentAddNewUser();
                }
                else {
                    Toast.makeText(getActivity(), "Maximum 5 Users can be added\nLong Press on user to delete", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUsersFragmentAddNewUserListener) {
            mListener = (OnUsersFragmentAddNewUserListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnUsersFragmentAddNewUserListener {
        // TODO: Update argument type and name
        void onFragmentAddNewUser();
    }
}
