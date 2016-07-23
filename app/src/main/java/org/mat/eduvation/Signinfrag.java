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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signinfrag extends Fragment {

    private EditText email,password;
    private FirebaseAuth auth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        if(SaveSharedPreference.getUserName(getContext()).length()> 0)
        {
            startActivity(new Intent(getContext(), navigation.class));
        }



    }

 /*   @Override
    public void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null && !Logout.check) {
            Logout.check = true;
            startActivity(new Intent(getContext(), navigation.class));

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null && !Logout.check) {
            Logout.check = true;
            startActivity(new Intent(getContext(), navigation.class));

        }
    }

    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_signinfrag, container, false);
        email=(EditText)view.findViewById(R.id.signinmailid);
        password=(EditText)view.findViewById(R.id.signinpassid);
        Button login = (Button) view.findViewById(R.id.signinbtnid);
        TextView forgotpass = (TextView) view.findViewById(R.id.signinforgotid);

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),Forgotpassword.class));
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

                            SaveSharedPreference.setUserName(getContext(),emailtext);
                            startActivity(new Intent(getContext(), navigation.class));
                        }
                    }
                });


    }

}