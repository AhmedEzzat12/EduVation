package org.mat.eduvation.navigation_items;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import org.mat.eduvation.ImageModel;
import org.mat.eduvation.R;
import org.mat.eduvation.UserModel;
import org.mat.eduvation.adapters.attendanceAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Attendance extends Fragment {
    private List<UserModel> UserModellist;
    private List<ImageModel> UserImagelist;
    private RecyclerView recyclerView;
    private attendanceAdapter adapter;
    private HashMap<String, ImageModel> UsersWithImagesList;
    private View root;
    public Attendance() {
        // Required empty public constructor
        UserModellist = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getContext());
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_attendance, container, false);

        Toast.makeText(getContext(), "Loading data please wait", Toast.LENGTH_LONG).show();

        recyclerView = (RecyclerView) root.findViewById(R.id.rectcleattendance);
        adapter = new attendanceAdapter(UserModellist, UsersWithImagesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Get a reference to our posts
        Firebase refUsers = new Firebase("https://eduvation-7aff9.firebaseio.com/users");
        Firebase refImages = new Firebase("https://eduvation-7aff9.firebaseio.com/images");

        if (isNetworkAvailable()) {

            refImages.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap<String, ImageModel> data = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, ImageModel>>() {
                    });
                    //UserImagelist=  dataSnapshot.getValue(ImageModel.class);
                    if (data != null) {
                        Log.d("ImageModel data", String.valueOf(data.size()));
                        UserImagelist = new ArrayList<>(data.values());
                        UsersWithImagesList = mergeUsersListWithImageList(UserModellist, UserImagelist);
                        adapter.updateData(UserModellist, UsersWithImagesList);
                        // recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(getContext(), "The read failed: " + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                }

            });
            refUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //   Toast.makeText(getContext(),"success",Toast.LENGTH_LONG).show();
                    HashMap<String, UserModel> data = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, UserModel>>() {
                    });
                    if (data != null) {
                        Log.d("UserModel data", String.valueOf(data.size()));
                        UserModellist = new ArrayList<>(data.values());
                        Collections.sort(UserModellist, new Comparator<UserModel>() {
                            public int compare(UserModel u1, UserModel u2) {
                                return u1.getName().compareTo(u2.getName());
                            }
                        });
                        UsersWithImagesList = mergeUsersListWithImageList(UserModellist, UserImagelist);
                        adapter.updateData(UserModellist, UsersWithImagesList);
                        //recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(getContext(), "The read failed: " + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                }

            });

            // recyclerView.setPadding(15,15,15,15);
            //   Toast.makeText(getContext(),UserModellist.get(1).getName(),Toast.LENGTH_LONG).show();

        } else
            Toast.makeText(getActivity(), "Please connect to internet to get attendees", Toast.LENGTH_LONG).show();
        return root;
    }

    private HashMap<String, ImageModel> mergeUsersListWithImageList(List<UserModel> userModellist, List<ImageModel> userImagelist) {
        HashMap<String, ImageModel> hashMap = new HashMap<>();
        for (int i = 0; i < userModellist.size(); i++) {
            for (int j = 0; j < userImagelist.size(); j++) {
                if (userModellist.get(i).getEmail().toLowerCase().equals(userImagelist.get(j).getEmail().toLowerCase())) {
                    hashMap.put(userModellist.get(i).getEmail().toLowerCase(), userImagelist.get(j));

                    Log.d("mergeUsersList", String.valueOf(hashMap.get(userModellist.get(i).getEmail().toLowerCase()).getEmail()));
                    Log.d("mergeUsersList", String.valueOf(hashMap.get(userModellist.get(i).getEmail().toLowerCase()).getImagestr()));

                }
            }
        }
        return hashMap;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


}
