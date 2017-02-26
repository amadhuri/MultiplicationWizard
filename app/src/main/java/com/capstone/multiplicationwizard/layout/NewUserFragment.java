package com.capstone.multiplicationwizard.layout;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.capstone.multiplicationwizard.GameActivity;
import com.capstone.multiplicationwizard.MainActivity;
import com.capstone.multiplicationwizard.R;
import com.capstone.multiplicationwizard.data.MWItemsContract;
import com.capstone.multiplicationwizard.data.MWSQLiteHelper;
import com.capstone.multiplicationwizard.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewUserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewUserFragment extends Fragment {

    View mView = null;

    private OnFragmentInteractionListener mListener;

    public NewUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_new_user, container, false);
        Button mContBtn = (Button)mView.findViewById(R.id.bt_new_user_cont);
        mContBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText tvNewUser = (EditText)mView.findViewById(R.id.new_user_et);
                Log.e("NewUserFragment","New USer name:"+tvNewUser.getText());
                MainActivity mainActivity = (MainActivity)getActivity();
                //long id = mainActivity.mwDB.createUser(newUser);
                ContentValues contentValues = new ContentValues();
                String newUserName = tvNewUser.getText().toString();
                contentValues.put(MWSQLiteHelper.KEY_USERNAME, newUserName);
                Uri id = mainActivity.getContentResolver().insert(MWItemsContract.USERS_CONTENT_URI,contentValues);
                Log.e("NewUserFragment","createUSer returned id:"+id);
                //Launch GameActivity
                Intent intent = new Intent(mainActivity.getApplicationContext(), GameActivity.class);
                User newUser = new User();
                newUser.setUsername(newUserName);
                newUser.setLevel(1);
                newUser.setScore(0);
                newUser.setId(id.toString());
                intent.putExtra("com.capstone.multiplicationwizard.model.user",newUser);
                startActivity(intent);
                return;
            }
        });
        return mView;
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
           /* throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
