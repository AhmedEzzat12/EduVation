package org.mat.eduvation.navigation_items;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.mat.eduvation.LocaL_Database.DatabaseConnector;
import org.mat.eduvation.LocaL_Database.dbHelper;
import org.mat.eduvation.NotificationModel;
import org.mat.eduvation.R;
import org.mat.eduvation.adapters.notificationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Announcements extends Fragment {


    private List<NotificationModel> NotificationModellist;
    private notificationAdapter adapter;
    private DatabaseConnector databaseConnector;

    private View root;


    public Announcements() {
        NotificationModellist = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new notificationAdapter(getActivity());
        databaseConnector = new DatabaseConnector(getActivity());
        databaseConnector.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_announcements, container, false);

        getNotificationsFromDatabase();
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.Recycler_View_Notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return root;
    }

    private void getNotificationsFromDatabase() {
        Cursor cursor = databaseConnector.getAllNotifications();
        if (cursor != null && cursor.getCount() > 0) {

            NotificationModellist = new ArrayList<>();
            try {

                if (cursor.moveToFirst()) {


                    int index_message = cursor.getColumnIndex(dbHelper.COLUMN_MESSAGE);
                    int index_date = cursor.getColumnIndex(dbHelper.COLUMN_DATE);


                    do {
                        String message = cursor.getString(index_message);
                        String date = cursor.getString(index_date);

                        NotificationModel notificationModel = new NotificationModel(message, date);

                        NotificationModellist.add(notificationModel);

                    } while (cursor.moveToNext());

                    databaseConnector.close();
                    adapter.setData(NotificationModellist);


                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(),
                        Toast.LENGTH_LONG).show();

            }

        } else {
            Toast.makeText(getActivity(), "is null", Toast.LENGTH_LONG).show();
        }
    }


}
