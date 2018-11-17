package com.example.manish.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText etemail,etpass;

     FirebaseAuth mauth;

     ProgressDialog pd;

     DatabaseReference child= FirebaseDatabase.getInstance().getReference().child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.firebasedemo.R.layout.activity_login);

        etemail = findViewById(com.example.firebasedemo.R.id.etvalue11);
        etpass = findViewById(com.example.firebasedemo.R.id.etvalue12);

        mauth = FirebaseAuth.getInstance();

        if(mauth.getCurrentUser() != null)
        {
         finish();
         startActivity(new Intent(LoginActivity.this,UserActivity.class));
        }

        pd = new ProgressDialog(this);
    }

    public void dologin(View view)
    {
        String email = etemail.getText().toString();
        String pass = etpass.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            etemail.setError("Email is required");
            etemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            etemail.setError("Not Valid Email Required");
            etemail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(pass))
        {
            etpass.setError("Pass is required");
            etpass.requestFocus();
            return;
        }
        if(pass.length() <6)
        {
            etpass.setError("Minimum 6 character requird");
            etpass.requestFocus();
            return;
        }

        pd.setTitle("Login Please Wait..");
        pd.show();
        mauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                pd.dismiss();
                if(task .isSuccessful())
                {
                    Toast.makeText(LoginActivity.this,"Login Sucessfully",Toast.LENGTH_SHORT).show();


                    finish();
                    startActivity(new Intent(LoginActivity.this,UserActivity.class));

                }
                else
                {
                    Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}
