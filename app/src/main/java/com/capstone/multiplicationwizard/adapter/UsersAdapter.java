package com.capstone.multiplicationwizard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.capstone.multiplicationwizard.R;
import com.capstone.multiplicationwizard.model.User;

import java.util.ArrayList;

/**
 * Created by Madhuri on 2/16/2017.
 */
public class UsersAdapter extends ArrayAdapter<User> {

    public UsersAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the data item for this position
        User user = getItem(position);
        //Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.child_listitem, parent, false);
        }
        //Lookup view for data population
        Button btName = (Button) convertView.findViewById(R.id.bt_child_name);
        btName.setText(user.name);
        return convertView;
    }
}
