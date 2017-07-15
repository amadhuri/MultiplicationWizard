package com.capstone.multiplicationwizard.layout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import com.capstone.multiplicationwizard.model.User;

import java.util.ArrayList;

public class UsersFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private View mRootView = null;
    private Cursor mCursor = null;
    private UsersAdapter usersAdapter;
    private static final int LOADER_ID = 0x02;
    ArrayList<User> arr_users = new ArrayList<>();


    private OnUsersFragmentAddNewUserListener mListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    private void setUserAdapterItems() {


        Cursor cursor = getActivity().getContentResolver().query(MWItemsContract.USERS_CONTENT_URI,
                null, null, null, null, null);
        if (cursor == null) {
            return;
        }
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            User user = new User();
            String username = cursor.getString(cursor.getColumnIndex("username"));
            Integer id = cursor.getInt(cursor.getColumnIndex("ID"));
            user.setUsername(username);
            user.setId(id.toString());
            arr_users.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        for (User user : arr_users) {
            usersAdapter.add(user);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayList<User> arr_users = new ArrayList<>();
        usersAdapter = new UsersAdapter(this.getContext(), arr_users);

        mRootView = inflater.inflate(R.layout.fragment_users, container, false);
        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        final ListView childListView = (ListView) mRootView.findViewById(R.id.childLV);
        childListView.setAdapter(usersAdapter);
        childListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User currentUser = (User) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(getActivity().getApplicationContext(), GameActivity.class);
                intent.putExtra("com.capstone.multiplicationwizard.model.user", currentUser);
                startActivity(intent);
                return;

            }
        });

        childListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                User currentUser = (User) adapterView.getAdapter().getItem(i);
                String mSelectionCause = MWItemsContract.USERS_BASE_PATH + "=?";
                String[] mSelectionArgs = new String[1];
                mSelectionArgs[0] = currentUser.getUserId();
                int retUser = getActivity().getContentResolver().delete(MWItemsContract.USERS_CONTENT_URI,
                        mSelectionCause, mSelectionArgs);

                int retScores = getActivity().getContentResolver().delete(MWItemsContract.SCORES_CONTENT_URI,
                        mSelectionCause, mSelectionArgs);
                if (retUser == 0 && retScores == 0) {
                    usersAdapter.remove(currentUser);
                    usersAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.users_not_deleted), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


        FloatingActionButton addFab = (FloatingActionButton) mRootView.findViewById(R.id.add_users_fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = getActivity().getContentResolver().query(MWItemsContract.USERS_CONTENT_URI,
                        null, null, null, null, null);
                if (cursor == null) {
                    return;
                } else if (cursor.getCount() < MWItemsContract.MAX_USERS) {
                    mListener.onFragmentAddNewUser();
                } else {
                    Toast.makeText(getActivity(),
                            getString(R.string.str_max) + MWItemsContract.MAX_USERS +
                                    getString(R.string.users_can_add)
                            , Toast.LENGTH_SHORT).show();
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this.getActivity().getApplicationContext(),
                MWItemsContract.USERS_CONTENT_URI,
                null,
                null,
                null,
                null
        );
        /*Cursor cursor = getActivity().getContentResolver().query(MWItemsContract.USERS_CONTENT_URI,
                null, null, null, null, null);
        if (cursor == null) {
            return;
        }*/
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) {
            return;
        }
        data.moveToFirst();
        for (int i = 0; i < data.getCount(); i++) {
            User user = new User();
            String username = data.getString(data.getColumnIndex("username"));
            Integer id = data.getInt(data.getColumnIndex("ID"));
            user.setUsername(username);
            user.setId(id.toString());
            arr_users.add(user);
            data.moveToNext();
        }
        data.close();
        for (User user : arr_users) {
            usersAdapter.add(user);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        usersAdapter.clear();
    }

    public interface OnUsersFragmentAddNewUserListener {
        // TODO: Update argument type and name
        void onFragmentAddNewUser();
    }
}
