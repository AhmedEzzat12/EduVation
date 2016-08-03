package org.mat.eduvation.navigation_items;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.mat.eduvation.LocaL_Database.DatabaseConnector;
import org.mat.eduvation.R;
import org.mat.eduvation.SaveSharedPreference;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private TextView name, company, email;
    private DatabaseConnector databaseConnector;
    private Button editProfile;
    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseConnector = new DatabaseConnector(getActivity());

        databaseConnector.open();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ;
        name = (TextView) root.findViewById(R.id.username);
        company = (TextView) root.findViewById(R.id.CompanyProfile);
        email = (TextView) root.findViewById(R.id.EmailProfile);
        editProfile = (Button) root.findViewById(R.id.editProfileButton);
        getUser();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("company", company.getText().toString());
                startActivity(intent);
            }
        });
        return root;
    }

    private void getUser() {

        Cursor cursor = databaseConnector.getUserByEmail(SaveSharedPreference.getUserName(getContext()));

        if (cursor != null && cursor.moveToFirst()) {

            String temp;
            TextView[] array = {name, company, email};
            for (int i = 0; i < 3; ++i) {
                temp = cursor.getString(i);
                array[i].setText(temp);
            }

        }

    }

}