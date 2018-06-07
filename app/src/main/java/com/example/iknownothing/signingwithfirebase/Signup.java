package com.example.iknownothing.signingwithfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
Button signup;
private FirebaseAuth firebaseAuth;
EditText firstName,lastName,email,password,repassword,contact;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        progressDialog=new ProgressDialog(this);
        signup=findViewById(R.id.signup);
        lastName=findViewById(R.id.lastName);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        repassword=findViewById(R.id.repassword);
        contact=findViewById(R.id.contact);

        firebaseAuth=FirebaseAuth.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(TextUtils.isEmpty(firstName.getText().toString().trim()))
            {
                Toast.makeText(getApplicationContext(),"Name Missing...",Toast.LENGTH_SHORT).show();
                return;
            }
            else if(TextUtils.isEmpty(email.getText().toString().trim()))
            {
                Toast.makeText(getApplicationContext(),"Email Missing...",Toast.LENGTH_SHORT).show();
                return;
                }
            else if (TextUtils.isEmpty(password.getText().toString().trim())&& TextUtils.isEmpty(repassword.getText().toString().trim())){

                Toast.makeText(getApplicationContext(),"Fill Password First...",Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.setMessage("Registering");
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //user is successfully registered......
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Registered Successfully........", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(Signup.this,MainActivity.class));
                        //progressDialog.dismiss();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Account already present........", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }

                }
            });

        }
    });
    }

}
