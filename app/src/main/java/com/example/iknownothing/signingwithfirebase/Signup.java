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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
Button signup;

//declaring FirebaseAuth and FirebaseFireStore objects......
private FirebaseAuth firebaseAuth;
private FirebaseFirestore firebaseFirestore;

EditText firstName,lastName,email,password,repassword,contact;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        progressDialog=new ProgressDialog(this);

        signup=findViewById(R.id.signup);
        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lastName);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        repassword=findViewById(R.id.repassword);
        contact=findViewById(R.id.contact);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

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
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Registering");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            final HashMap<String,Object> hashes=new HashMap<>();
            hashes.put("First",firstName.getText().toString().trim());
            hashes.put("Last",lastName.getText().toString().trim());
            hashes.put("Email",email.getText().toString().trim());
            hashes.put("Password",password.getText().toString().trim());
            hashes.put("Contact",contact.getText().toString().trim());

            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //user is successfully registered......
                        firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).set(hashes).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Firebase Firestore Successfull........", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Firebase Firestore Unsuccessfull........", Toast.LENGTH_SHORT).show();

                            }
                        });
                        progressDialog.setMessage("Taking you to Login Page...");
                        Toast.makeText(getApplicationContext(), "Firebase Authentication Successfull........", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(Signup.this,MainActivity.class));
                        progressDialog.dismiss();
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
