package com.example.android.electricreader;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declare Objects

    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;
    private Button view;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize firebase Auth

        firebaseAuth = FirebaseAuth.getInstance();

        //Initialize the Progressbar Object

        progressDialog = new ProgressDialog(this);

        //Initialize textViews, eDitText and buttonLogin

        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        textViewSignIn = (TextView)findViewById(R.id.textViewSignIn);

        //Implements Onclick Listener

        buttonLogin.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
    }

    private void register(){

        //Get the emial and password for the user

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //Check if the data strings are empty or not
        if (TextUtils.isEmpty(email)){

            //If email is empty

            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();

            //Stop the method from executing
            return;

        }

        if (TextUtils.isEmpty(password)){

            //If password is empty
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();

            //Stop the method from executing

            return;
        }

        //Show progressDialog message

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //If user is looged in successfully and registered
                            //Start profile activity
                            //Display toast

                            Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        }else {

                            Toast.makeText(MainActivity.this, "Failed to register.. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {

        if (view == buttonLogin){

            register();
        }

        if (view == textViewSignIn){

            //signIn the user/ Open the login activity
        }

    }

}
