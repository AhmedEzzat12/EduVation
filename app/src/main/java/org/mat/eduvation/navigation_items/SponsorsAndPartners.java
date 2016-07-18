package org.mat.eduvation.navigation_items;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mat.eduvation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SponsorsAndPartners extends Fragment {


    public SponsorsAndPartners() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sponsors_and_partners, container, false);
    }

}
