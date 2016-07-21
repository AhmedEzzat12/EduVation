package org.mat.eduvation.navigation_items;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import org.mat.eduvation.Main;
import org.mat.eduvation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Logout extends Fragment {

    public static Boolean check = false;
    public Logout() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        check = true;
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), Main.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

}