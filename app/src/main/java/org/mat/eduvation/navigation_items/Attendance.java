package org.mat.eduvation.navigation_items;


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

import org.mat.eduvation.R;
import org.mat.eduvation.adapters.attendanceadapter;
import org.mat.eduvation.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Attendance extends Fragment {
users user;
   List<users> userslist;
    private DatabaseReference mDatabase;
    attendanceadapter adapter;
    View root;
FirebaseDatabase database;

    public Attendance() {
        // Required empty public constructor
        userslist=new ArrayList<>();
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
          root= inflater.inflate(R.layout.fragment_attendance, container, false);

        // Get a reference to our posts
        Firebase ref = new Firebase("https://eduvation-7aff9.firebaseio.com/users");

ref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        System.out.println(dataSnapshot.getValue());
     //   Toast.makeText(getContext(),"success",Toast.LENGTH_LONG).show();
        HashMap<String,users>data=dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, users>>() {
        });
        userslist=new ArrayList<users>(data.values());
        adapter=new attendanceadapter(userslist,getContext());
        RecyclerView recyclerView = (RecyclerView)root.findViewById(R.id.rectcleattendance);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
       // recyclerView.setPadding(15,15,15,15);

     //   Toast.makeText(getContext(),userslist.get(1).getName(),Toast.LENGTH_LONG).show();


    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        Toast.makeText(getContext(),"The read failed: " + firebaseError.getMessage(),Toast.LENGTH_LONG).show();

    }
});





        return root;
    }


}
