package com.example.iknownothing.signingwithfirebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.TestLooperManager;
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
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
Button login;
private FirebaseAuth firebaseAuth;
EditText email,password;
TextView register,forgotPassword;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        login=findViewById(R.id.login);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        forgotPassword=findViewById(R.id.forgotPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString().trim()))
                {
                    Toast.makeText(getApplicationContext(), "Email Missing", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(password.getText().toString().trim()))
                {
                    Toast.makeText(getApplicationContext(), "Enter Valid Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setCancelable(true);
                progressDialog.setMessage("Signing-In...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            finish();
                            startActivity(new Intent(Login.this,MainActivity.class));
                            Toast.makeText(getApplicationContext(),"You're now Signed In",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Wrong Email or Password !",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Login.this,Signup.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(email.getText().toString().trim()))
                {
                    Toast.makeText(getApplicationContext(),"Enter Email Please.?",Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.sendPasswordResetEmail(email.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Link has been sent to your Email",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }});
    }
}
