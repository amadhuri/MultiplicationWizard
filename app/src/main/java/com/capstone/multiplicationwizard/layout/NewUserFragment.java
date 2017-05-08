package com.capstone.multiplicationwizard.layout;

import android.content.ContentValues;
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
import android.widget.Toast;

import com.capstone.multiplicationwizard.GameActivity;
import com.capstone.multiplicationwizard.MainActivity;
import com.capstone.multiplicationwizard.R;
import com.capstone.multiplicationwizard.data.MWItemsContract;
import com.capstone.multiplicationwizard.data.MWSQLiteHelper;
import com.capstone.multiplicationwizard.data.MWSQLiteHelperNew;
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
    MWSQLiteHelperNew helperNew;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        helperNew = new MWSQLiteHelperNew(getActivity());
        mView =  inflater.inflate(R.layout.fragment_new_user, container, false);
        Button mContBtn = (Button)mView.findViewById(R.id.bt_new_user_cont);
        mContBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText tvNewUser = (EditText)mView.findViewById(R.id.new_user_et);

                String name = tvNewUser.getText().toString().trim();
                if(name.equals(""))
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter user name", Toast.LENGTH_SHORT).show();
                }
                else {
                    /*
                    Log.e("NewUserFragment", "New USer name:" + tvNewUser.getText());
                    MainActivity mainActivity = (MainActivity) getActivity();
                    //long id = mainActivity.mwDB.createUser(newUser);
                    ContentValues contentValues = new ContentValues();
                    String newUserName = tvNewUser.getText().toString();
                    contentValues.put(MWSQLiteHelper.KEY_USERNAME, newUserName);
                    contentValues.put(MWSQLiteHelper.KEY_LEVEL,1);
                    Uri id = mainActivity.getContentResolver().insert(MWItemsContract.USERS_CONTENT_URI, contentValues);
                    Log.e("NewUserFragment", "createUSer returned id:" + id);
                    //Launch GameActivity
                    Intent intent = new Intent(mainActivity.getApplicationContext(), GameActivity.class);
                    User newUser = new User();
                    newUser.setUsername(newUserName);
                    newUser.setMaxLevel(1);
                    newUser.setHighScore(0);
                    newUser.setId(id.toString());
                    intent.putExtra("com.capstone.multiplicationwizard.model.user", newUser);
                    startActivity(intent);
                    */
                    if(helperNew.getUsers().size() < 5) {
                        long id = helperNew.createUser(name);
                        if (id != -1) {
                            Intent intent = new Intent(getActivity(), GameActivity.class);
                            User newUser = new User();
                            newUser.setUsername(name);
                            newUser.setMaxLevel(1);
                            newUser.setHighScore(0);
                            newUser.setId("" + id);
                            intent.putExtra("com.capstone.multiplicationwizard.model.user", newUser);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "Duplicate User Name", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Maximum 5 Users can be added", Toast.LENGTH_SHORT).show();
                    }

                }
                return;
            }
        });
        return mView;
    }
}
