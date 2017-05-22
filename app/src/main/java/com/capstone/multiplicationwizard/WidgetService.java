package com.capstone.multiplicationwizard;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.capstone.multiplicationwizard.data.MWSQLiteHelperNew;
import com.capstone.multiplicationwizard.model.User;

import java.util.ArrayList;

public class WidgetService extends RemoteViewsService {

    public WidgetService() {

    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetViewFactory();
    }
    class WidgetViewFactory implements RemoteViewsFactory {

        MWSQLiteHelperNew mwDB;
        ArrayList<User> userArrayList;
        int numOfUsers;

        public WidgetViewFactory() {
        }

        @Override
        public void onDestroy() {
            userArrayList.clear();
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public void onCreate() {
            mwDB = new MWSQLiteHelperNew(getApplicationContext());
            userArrayList = mwDB.getUsers();
            numOfUsers = userArrayList.size();
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getCount() {
            return numOfUsers;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            if(i < numOfUsers) {
                User user = userArrayList.get(i);
                RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.appwidget_layout);
                remoteViews.setTextViewText(R.id.tv_appwidget_1,user.getUsername());
                remoteViews.setTextViewText(R.id.tv_appwidget_2,user.getHighScore().toString());

                return remoteViews;
            }
            return null;
        }

        @Override
        public void onDataSetChanged() {

        }
    }

}
