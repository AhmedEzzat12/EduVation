package org.mat.eduvation.navigation_items;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.mat.eduvation.NotificationModel;
import org.mat.eduvation.R;
import org.mat.eduvation.adapters.notificationAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Announcements extends Fragment {
    private List<NotificationModel> notificationModellist;
    private DatabaseReference mDatabase;
    private notificationAdapter adapter;
    private View root;
    private FirebaseDatabase database;

    public Announcements() {
        // Required empty public constructor
        notificationModellist = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_announcements, container, false);

        // Get a reference to our posts
        Firebase ref = new Firebase("https://eduvation-7aff9.firebaseio.com/Notifications");
        if (isNetworkAvailable()) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //   Toast.makeText(getContext(),"success",Toast.LENGTH_LONG).show();
                HashMap<String, NotificationModel> data = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, NotificationModel>>() {
                });

                if (data.size() > 0) {
                    notificationModellist = new ArrayList<>(data.values());

                    Collections.sort(notificationModellist, new Comparator<NotificationModel>() {
                        public int compare(NotificationModel n1, NotificationModel n2) {
                            return n1.get_id().compareTo(n2.get_id());
                        }
                    });

                    adapter = new notificationAdapter(notificationModellist, getContext());
                    RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.Recycler_View_Notifications);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "there are no notifications"
                            , Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getContext(), "The read failed: " + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
        } else {
            Toast.makeText(getContext(), "Please connect to internet to get data ", Toast.LENGTH_LONG).show();

        }
        return root;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

}