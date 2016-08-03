package org.mat.eduvation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.mat.eduvation.LocaL_Database.DatabaseConnector;


public class Signupfrag extends Fragment {

    private TextView name, email, passwoard, confirmpass, company, birthday;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private UserModel userModel;
    private DatabaseReference users;
    private String[] fields;
    private DatabaseConnector databaseConnector;

    public static String keyGenerator(String[] s) {
        String key = "edu";
        for (String value : s) {
            key += value;
        }
        return key;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseConnector = new DatabaseConnector(getActivity());
        databaseConnector.open();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_signupfrag, container, false);
        name=(TextView) root.findViewById(R.id.signupusername);
        email= (TextView) root.findViewById(R.id.signupemailid);
        passwoard=(TextView)root.findViewById(R.id.signuppassid);
        confirmpass=(TextView)root.findViewById(R.id.signupconfirmpassid);
        company = (TextView) root.findViewById(R.id.signupCompany);
        birthday = (TextView) root.findViewById(R.id.birthday);
        Button register = (Button) root.findViewById(R.id.signupbtnid);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");

        userModel = new UserModel();

        auth=FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwoard.getText().toString().equals(confirmpass.getText().toString()))
                {
                    signUp();
                }
                else {
                    Toast.makeText(getActivity(), "password doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    public  void signUp(){

        String mail=email.getText().toString();
        String pass=passwoard.getText().toString();
        final String username=name.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getContext(), "Enter Your Name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(getContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.length() < 6) {
            Toast.makeText(getContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();


                    writeNewUser(name.getText().toString(), email.getText().toString().toLowerCase(),
                            company.getText().toString(), String.valueOf(birthday.getText()));

                    databaseConnector.insertUser(name.getText().toString(), company.getText().toString(), String.valueOf(birthday.getText())
                            , email.getText().toString(), UserModel.KEY);

                    SaveSharedPreference.setUserName(getContext(), email.getText().toString().toLowerCase());

                    startActivity(new Intent(getContext(), navigation.class));
                }
                else {
                    // if email already registerd
                    if (task.getException().getMessage().equals("The email address is already in use by another account.")) {
                        Toast.makeText(getActivity(), "The email address is already exist", Toast.LENGTH_SHORT).show();

                    }

                    Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void writeNewUser(String name, String email, String company, String birthday) {
        userModel = new UserModel(name, company, birthday, email.toLowerCase());
        fields = email.split("\\.");
        UserModel.KEY = keyGenerator(fields);
        users.child(UserModel.KEY).setValue(userModel);
    }
}