package com.example.moifoods.Aunthetication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moifoods.R;
import com.example.moifoods.Screens.MainMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Signup extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView loginpage;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User");
        progressDialog.setCancelable(false);


        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        loginpage = findViewById(R.id.loginpage);
        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signup.this,Login.class);
                startActivity(i);
            }
        });


        Button signup = findViewById(R.id.signupBTN);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reginsterUser();
            }
        });


    }


    private void reginsterUser(){

        //getting user inputs
        String Pemail = email.getText().toString().trim();
        String pPassword = password.getText().toString();


        if(Pemail.isEmpty()){
            // email.setError("Email in required");
            email.requestFocus();
            return;
        }
        //checking valid email

        if(!Patterns.EMAIL_ADDRESS.matcher(Pemail).matches()){
            email.setError("Enter valid Email");
            email.requestFocus();
            return;

        }
        if(pPassword.isEmpty()){
            //  password.setError("Password is required");
            password.requestFocus();
            return;

        }

        if(pPassword.length()>6){
            password.setError("Password requires maximum of 6");
            password.requestFocus();
            return;

        }


        else {
            progressDialog.show();


            mAuth.createUserWithEmailAndPassword(Pemail,pPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        finish();
                        Toast.makeText(getApplicationContext(),"Signup Succesful",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Signup.this, MainMenu.class);
                        startActivity(i);
                        progressDialog.dismiss();
                    }else {

                        if(task.getException() instanceof FirebaseAuthUserCollisionException){

                            Toast.makeText(getApplicationContext(),"Already Signedup",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Signup.this, Login.class);
                            startActivity(i);
                            progressDialog.dismiss();

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }

                    }

                }
            });
        }





    }

}
