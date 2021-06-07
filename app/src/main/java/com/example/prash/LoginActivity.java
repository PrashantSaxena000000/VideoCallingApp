package com.example.prash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText emailBox,passwordBox;
    Button loginbtn,createbtn;
    CheckBox remember;

    FirebaseAuth auth;
    ProgressDialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Please wait....");

        auth=FirebaseAuth.getInstance();
        emailBox=findViewById(R.id.emailBox);
        passwordBox=findViewById(R.id.passwordBox);

        loginbtn=findViewById(R.id.loginbtn);
        createbtn=findViewById(R.id.createbtn);
        remember =findViewById(R.id.remember);
        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox=  preferences.getString("remember"," ");
        if (checkbox.equals("true")){
            Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);


        }else if(checkbox.equals("false")){
            Toast.makeText(this, "Please sign in", Toast.LENGTH_SHORT).show();
        }

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String email,password;
                email=emailBox.getText().toString();
                password=passwordBox.getText().toString();

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });



        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor =preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "checked", Toast.LENGTH_SHORT).show();
                }
                 else if (!compoundButton.isChecked())
                 {
                     SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                     SharedPreferences.Editor editor =preferences.edit();
                     editor.putString("remember","false");
                     editor.apply();
                     Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}