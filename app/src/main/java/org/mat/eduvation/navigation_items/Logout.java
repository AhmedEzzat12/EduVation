package org.mat.eduvation.navigation_items;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.mat.eduvation.Main;
import org.mat.eduvation.R;
import org.mat.eduvation.SaveSharedPreference;

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
        clearUserName(getContext());

        check = true;
        FirebaseAuth.getInstance().signOut();

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        startActivity(new Intent(getActivity(), Main.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        getActivity().finish();
    }

    public  void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = SaveSharedPreference.getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
        Toast.makeText(getContext(),"member deleted", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }


}