package org.mat.eduvation.navigation_items;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.mat.eduvation.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class home extends Fragment {

    private TextView home_desc1_textV;
    private TextView home_desc2_textV;
    private TextView home_desc3_textV;
    private FirebaseDatabase database;
    private ValueEventListener value1;
    private ValueEventListener value2;
    private ValueEventListener value3;
    private DatabaseReference home_desc1;
    private DatabaseReference home_desc2;
    private DatabaseReference home_desc3;
    public home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        home_desc1_textV = (TextView) root.findViewById(R.id.home_desc1);
        home_desc2_textV = (TextView) root.findViewById(R.id.home_desc2);
        home_desc3_textV = (TextView) root.findViewById(R.id.home_desc3);
        database = FirebaseDatabase.getInstance();
        home_desc1 = database.getReference("home_desc1");
        home_desc2 = database.getReference("home_desc2");
        home_desc3 = database.getReference("home_desc3");

        home_desc1.setValue("New vision for Education Development through Technology by creating a great summit environment gathering all who interested in Edu-Tech future.");
        home_desc2.setValue("EduVation  Education&Innovation  is: A new call for using innovation in the educational industry. A new shout for the nontraditional ways through which we can learn. A deep insight into what is coming next in education. Targeting all people interested in Educational and Technological fields and seeking for its development.");
        home_desc3.setValue("To create an environment where passionate people for education can come together to learn, network, bridge the gap between trades, expose potential and see actual results.");

        value1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String result = dataSnapshot.getValue(String.class);
                home_desc1_textV.setText(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        home_desc1.addValueEventListener(value1);

        value2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String result = dataSnapshot.getValue(String.class);
                home_desc2_textV.setText(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        home_desc2.addValueEventListener(value2);

        value3 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String result = dataSnapshot.getValue(String.class);
                home_desc3_textV.setText(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        home_desc3.addValueEventListener(value3);

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (value1 != null || value2 != null || value3 != null) {

            home_desc1.removeEventListener(value1);
            home_desc2.removeEventListener(value2);
            home_desc3.removeEventListener(value3);

        }
    }
}
