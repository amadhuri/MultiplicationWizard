package com.capstone.multiplicationwizard.layout;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.capstone.multiplicationwizard.GameActivity;
import com.capstone.multiplicationwizard.R;
import com.capstone.multiplicationwizard.data.MWItemsContract;
import com.capstone.multiplicationwizard.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewUserFragment extends Fragment {

    View mView = null;

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

                String name = tvNewUser.getText().toString().toLowerCase().trim();
                if(name.equals(""))
                {
                    Toast.makeText(getActivity().getApplicationContext(),
                                     "Enter user name", Toast.LENGTH_SHORT).show();
                }
                else {
                    Cursor cursor = getActivity().getContentResolver().query
                                        (MWItemsContract.USERS_CONTENT_URI,
                                        null,null,null,null,null);
                    if(cursor.getCount() < MWItemsContract.MAX_USERS) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MWItemsContract.USER_NAME,name);
                        Uri newUri;
                        newUri = getActivity().getContentResolver().insert
                                                    (MWItemsContract.USERS_CONTENT_URI,
                                                     contentValues);
                        if(newUri == null) {
                            Toast.makeText(getContext(),
                                    "Failed to insert"+name,
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        long id = ContentUris.parseId(newUri);
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
                            Toast.makeText(getActivity(), "Duplicate User Name",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(),
                                      "Maximum"+MWItemsContract.MAX_USERS+
                                      "Users can be added", Toast.LENGTH_SHORT).show();
                    }

                }
                return;
            }
        });
        return mView;
    }
}
