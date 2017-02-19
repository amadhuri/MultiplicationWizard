package com.capstone.multiplicationwizard.layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.capstone.multiplicationwizard.R;
import com.capstone.multiplicationwizard.adapter.UsersAdapter;
import com.capstone.multiplicationwizard.model.User;

import java.util.ArrayList;

public class UsersFragment extends Fragment {

    View mRootView = null;

    private OnUsersFragmentAddNewUserListener mListener = null;

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String[] mobileArray = {"Pranav","Abhinav"};
        ArrayList<User> arrayOfUsers = new ArrayList<User>();

        UsersAdapter adapter = new UsersAdapter(this.getContext(), arrayOfUsers);
        for(String name:mobileArray) {
            User newUser = new User(name);
            adapter.add(newUser);
        }
        mRootView = inflater.inflate(R.layout.fragment_users, container, false);
       // ArrayAdapter adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.child_listitem, mobileArray);
        ListView childListView = (ListView) mRootView.findViewById(R.id.childLV);
        childListView.setAdapter(adapter);

        FloatingActionButton addFab = (FloatingActionButton) mRootView.findViewById(R.id.add_users_fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentAddNewUser();
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
