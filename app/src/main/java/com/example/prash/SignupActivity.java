package com.example.prash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    FirebaseFirestore database;

    FirebaseAuth auth;
    EditText emailBox,passwordBox,nameBox;
    Button loginbtn,createbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        emailBox=findViewById(R.id.emailBox);
        nameBox=findViewById(R.id.nameBox);
        passwordBox=findViewById(R.id.passwordBox);

        loginbtn=findViewById(R.id.loginbtn);
        createbtn=findViewById(R.id.createbtn);

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password,name;
                email=emailBox.getText().toString();
                password=passwordBox.getText().toString();
                name=nameBox.getText().toString();

                User user =new User();
                user.setEmail(email);
                user.setPasswprd(password);
                user.setName(name);


                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.collection("users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(SignupActivity.this,LoginActivity.class) );

                                }
                            });
                            Toast.makeText(SignupActivity.this, "Account is Created", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}