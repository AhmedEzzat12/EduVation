package org.mat.eduvation.navigation_items;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.mat.eduvation.NotificationModel;
import org.mat.eduvation.R;
import org.mat.eduvation.adapters.notificationAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Announcements extends Fragment {

    private ArrayList<NotificationModel> NotificationModelList;
    private DatabaseReference mDatabase;
    Firebase ref ;
    private notificationAdapter adapter;
    private View root;
    private FirebaseDatabase database;

    public Announcements() {
        // Required empty public constructor
        NotificationModelList = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        Firebase ref = new Firebase("https://eduvation-7aff9.firebaseio.com/Notification");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_announcements, container, false);

        adapter = new notificationAdapter(retrieve(), getContext());
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.Recycler_View_Notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


       // Get a reference to our posts
       /* ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //   Toast.makeText(getContext(),"success",Toast.LENGTH_LONG).show();
                HashMap<String, NotificationModel> data =
                        dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, NotificationModel>>() {
                });
                NotificationModelList = new ArrayList<>(data.values());

                adapter = new notificationAdapter(NotificationModelList, getContext());
                RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.Recycler_View_Notifications);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
                // recyclerView.setPadding(15,15,15,15);
                //   Toast.makeText(getContext(),UserModellist.get(1).getName(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getContext(),"The read failed: " + firebaseError.getMessage(),Toast.LENGTH_LONG).show();
            }

        });*/
        return root;
    }


    public void FetchData(DataSnapshot datasnapshot){

        NotificationModelList.clear();
        for(DataSnapshot ds :datasnapshot.getChildren()){
          NotificationModel nm = ds.getValue(NotificationModel.class);
            NotificationModelList.add(nm);

        }
    }

    public  ArrayList<NotificationModel> retrieve(){

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
          FetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                FetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return NotificationModelList;
    }



}
