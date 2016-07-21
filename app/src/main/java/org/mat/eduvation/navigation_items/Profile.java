package org.mat.eduvation.navigation_items;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.mat.eduvation.R;
import org.mat.eduvation.UserModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference users;
    private TextView name, company, email;
    private FirebaseAuth auth;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        database = FirebaseDatabase.getInstance();
        name = (TextView) root.findViewById(R.id.username);
        company = (TextView) root.findViewById(R.id.CompanyProfile);
        email = (TextView) root.findViewById(R.id.EmailProfile);

        users = database.getReference("users");
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.child(keyGenerator())
                        .getValue(UserModel.class);
                name.setText(userModel.getName());
                company.setText(userModel.getCompany());
                email.setText(userModel.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });


        return root;
    }

    private String keyGenerator() {
        String key = "edu";
        String email = auth.getCurrentUser().getEmail();
        String[] fields = email.split("\\.");
        for (String value : fields) {
            key += value;
        }
        return key;
    }


}