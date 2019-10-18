package com.example.moifoods.Aunthetication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moifoods.R;
import com.example.moifoods.Screens.MainMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import maes.tech.intentanim.CustomIntent;

public class Login extends AppCompatActivity {

    Button LoginButton;
    TextView signuptxt,newPassword;
    EditText password,email;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        password = findViewById(R.id.password);
        email = findViewById(R.id.email);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating");
        progressDialog.setCancelable(false);


        newPassword = findViewById(R.id.newPassword);
        newPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FogotPassword();
            }
        });



        LoginButton  = findViewById(R.id.LoginButton);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
                        }
                    });




        signuptxt = findViewById(R.id.signuptxt);
        signuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Signup.class);
                CustomIntent.customType(Login.this,"fadein-to-fadeout");
                startActivity(i);
            }
        });


    }




    @Override
    protected void onStart() {
        if(mAuth.getCurrentUser()!=null){
            finish();
            Intent i = new Intent(Login.this, MainMenu.class);
            startActivity(i);


        }

        super.onStart();
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setMessage("Are you sure you want to exit?");
        b.setTitle("Moi Foods");
        b.setIcon(R.mipmap.moi_foods);
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(getApplicationContext(),"Exit",Toast.LENGTH_SHORT).show();
                System.exit(0);

            }
        });
        b.setNegativeButton("No",null);
        b.create();
        b.show();

    }


    private void Login(){


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
            password.setError("Password is required");
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

            mAuth.signInWithEmailAndPassword(Pemail,pPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        finish();
                        Toast.makeText(getApplicationContext(),"Login Succesful",Toast.LENGTH_SHORT).show();
                        Intent i =new Intent(Login.this, MainMenu.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       progressDialog.dismiss();

                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_LONG).show();
                       progressDialog.dismiss();

                    }
                }
            });
        }
    }



    public void FogotPassword(){

        Button login, cancel;
        final EditText email;

        LayoutInflater inflater = LayoutInflater.from(this);

        final View itemView = inflater.inflate(R.layout.reset_password, null);


        email = itemView.findViewById(R.id.remail);
        login = itemView.findViewById(R.id.Elogin);
        cancel = itemView.findViewById(R.id.Ecancel);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("sending reset email");
                progressDialog.show();




                mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    progressDialog.dismiss();

                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Login.this,"Check Your Email",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
            }
        });


        alertDialog = new AlertDialog.Builder(Login.this).setView(itemView).create();
        alertDialog.show();
    }


}

