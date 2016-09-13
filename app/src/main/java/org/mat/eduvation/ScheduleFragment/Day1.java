package org.mat.eduvation.ScheduleFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mat.eduvation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Day1 extends Fragment {


    public Day1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_day1, container, false);
/*        ImageView im = (ImageView)root.findViewById(R.id.icon2);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialog alert = new ViewDialog();
                alert.showDialog(getActivity());
            }
        });
*/
                return root;
    }

}
