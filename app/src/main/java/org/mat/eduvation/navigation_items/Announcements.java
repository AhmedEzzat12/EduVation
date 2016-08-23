package org.mat.eduvation.navigation_items;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mat.eduvation.FirebaseMessaging.MyFCMService;
import org.mat.eduvation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Announcements extends Fragment {

    private MyFCMService myFCMService;
    public Announcements() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_announcements, container, false);
    }

}
