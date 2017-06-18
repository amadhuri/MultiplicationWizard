package com.capstone.multiplicationwizard;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.capstone.multiplicationwizard.data.MWItemsContract;
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


        ArrayList<User> userArrayList = new ArrayList<User>();
        int numOfUsers = 1;

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
            Cursor cursor =getContentResolver().query(MWItemsContract.USERS_CONTENT_URI,
                                    null,null,null,null,null);
            if(cursor == null)
                return;
            cursor.moveToFirst();
            for(int i =0; i < cursor.getCount(); i++) {
                User user = new User();
                String username = cursor.getString(cursor.getColumnIndex(MWItemsContract.USER_NAME));
                user.setUsername(username);
                userArrayList.add(user);
                cursor.moveToNext();
            }
            cursor.close();
            numOfUsers = cursor.getCount();
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getCount() {
            Log.e("WidgetService","getCount numOfUSers:"+numOfUsers);
            return 1;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            Log.e("WidgetService","i:"+i);
            int j = 0;
          RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.appwidget_layout);
          remoteViews.removeAllViews(R.id.ll_child_views);
           if(j < numOfUsers) {
               Log.e("WidgetService","adding view:j"+j);
                User user = userArrayList.get(j);
                Log.e("WidgetService","username:"+ user.getUsername());
                RemoteViews childView = new RemoteViews(getPackageName(), R.layout.appwidget_listview_title);
                childView.setTextViewText(R.id.tv_appwidget_1,user.getUsername());
                childView.setTextViewText(R.id.tv_appwidget_2,"10");
                remoteViews.addView(R.id.ll_widget_layout, childView);
               j++;
            }
            else {
               RemoteViews childView = new RemoteViews(getPackageName(),R.layout.appwidget_nousers);
               remoteViews.addView(R.id.ll_child_views, childView);
           }
            return remoteViews;
        }

        @Override
        public void onDataSetChanged() {

        }
    }

}
