package org.mat.eduvation.navigation_items;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.mat.eduvation.R;

/**
 * A simple {@link Fragment} subclass.
 */


public class EditProfile extends Fragment {
    private EditText name, company;
    private Button finish_btn;

    public EditProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);



        // Inflate the layout for this fragment
        return view;
    }

}
