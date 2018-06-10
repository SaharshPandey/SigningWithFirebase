package com.example.iknownothing.signingwithfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    TextView user,user2,user3,user4;
    Button logOut;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creating instance of firebase authentication................
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        if(firebaseAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,Login.class));
        }


            user=findViewById(R.id.user);
            user2=findViewById(R.id.user2);
            user3=findViewById(R.id.user3);
            user4=findViewById(R.id.user4);
            logOut=findViewById(R.id.logOut);

            firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        user.setText("Welcome "+ task.getResult().get("First").toString().trim() +" "+task.getResult().get("Last").toString().trim());
                        user2.setText(task.getResult().get("Email").toString().trim());
                        user3.setText(task.getResult().get("Contact").toString().trim());
                        user4.setText(task.getResult().get("Password").toString().trim());

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"No information",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            logOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(MainActivity.this, Login.class));
                }
            });





    }
}
