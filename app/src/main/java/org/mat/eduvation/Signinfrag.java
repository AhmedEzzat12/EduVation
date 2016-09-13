package org.mat.eduvation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.mat.eduvation.LocaL_Database.DatabaseConnector;

import static org.mat.eduvation.Signupfrag.keyGenerator;

public class Signinfrag extends Fragment {

    private final String TAG_LOG = "SignInFragment";
    private EditText email,password;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference users;
    private String[] fields;
    private DatabaseConnector databaseConnector;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseConnector = new DatabaseConnector(getActivity());
        databaseConnector.open();
        if(SaveSharedPreference.getUserName(getContext()).length()> 0)
        {
            startActivity(new Intent(getContext(), navigation.class));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_signinfrag, container, false);
        email=(EditText)view.findViewById(R.id.signinmailid);
        password=(EditText)view.findViewById(R.id.signinpassid);
        Button login = (Button) view.findViewById(R.id.signinbtnid);
        TextView forgotpass = (TextView) view.findViewById(R.id.signinforgotid);

        database = FirebaseDatabase.getInstance();

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable())
                signIn();
                else
                    Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_LONG).show();

            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable())
                    startActivity(new Intent(getContext(), Forgotpassword.class));
                else
                    Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_LONG).show();

            }
        });
        return view;

    }
    public void signIn(){

        final String emailtext=email.getText().toString();
        final String passtext=password.getText().toString();

        if (TextUtils.isEmpty(emailtext)) {
            Toast.makeText(getContext(), "Enter Your Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passtext)) {
            Toast.makeText(getContext(), "Enter email password!", Toast.LENGTH_SHORT).show();
            return;
        }
        //authenticate user
        auth.signInWithEmailAndPassword(emailtext, passtext)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (passtext.length() < 6) {

                                Toast.makeText(getContext(),"enter more than 6 character", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getContext(),"Failure", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            SaveSharedPreference.setUserName(getContext(), emailtext.toLowerCase());

                            if (!databaseConnector.isEmailExist(emailtext.toLowerCase())) {
                                saveCurrentDataToFB_DB(emailtext.toLowerCase());
                            }

                            startActivity(new Intent(getActivity(), navigation.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            getActivity().finish();

                        }
                    }
                });
    }

    public void saveCurrentDataToFB_DB(String email) {
        fields = email.split("\\.");
        final String key = keyGenerator(fields);
        users = database.getReference("users");
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.child(String.valueOf(key)).getValue(UserModel.class);
                Log.d(TAG_LOG, userModel.getEmail());
                databaseConnector.insertUser(userModel.getName(), userModel.getCompany(), String.valueOf(userModel.getB_date()), userModel.getEmail(), key);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

}