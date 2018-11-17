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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference root = firebaseDatabase.getReference();

    DatabaseReference child = root.child("Users");
    DatabaseReference child2 = root.child("user2");

    ProgressDialog pd ;


    EditText etname, etemail, etpass, etconpass;

    int count =1;

    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.firebasedemo.R.layout.activity_main);

        etname = findViewById(com.example.firebasedemo.R.id.etvalue1);
        etemail = findViewById(com.example.firebasedemo.R.id.etvalue2);
        etpass = findViewById(com.example.firebasedemo.R.id.etvalue3);
        etconpass = findViewById(com.example.firebasedemo.R.id.etvalue4);

        mauth = FirebaseAuth.getInstance();


        if(mauth.getCurrentUser() != null)
        {
         startActivity(new Intent(MainActivity.this,UserActivity.class));
         finish();
        }

        pd = new ProgressDialog(this);

    }
    void createUser()
    {
        final String name = etname.getText().toString();
        String email = etemail.getText().toString();
        String pass = etpass.getText().toString();
        String conpass = etconpass.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            etemail.setError("Email Required");
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
            etpass.setError("Password Required");
            etpass.requestFocus();
            return;
        }
        if(pass.length() <6)
        {
            etpass.setError("Min 6 Character Password Required");
            etpass.requestFocus();
            return;
        }

        if(!pass.equals(conpass))
        {
            Toast.makeText(this,"Pass and Con Pass must be same", Toast.LENGTH_SHORT).show();
            return;
        }

        pd.setTitle("Registering Please Wait");
        pd.show();
        mauth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
             pd.dismiss();
               if(task.isSuccessful())
               {
                  String userid = mauth.getCurrentUser().getUid();

                  Person p = new Person(name);
                  child.child(userid).child("name").setValue(name);
                   Toast.makeText(MainActivity.this,"User Account created", Toast.LENGTH_SHORT).show();
                   finish();
                   startActivity(new Intent(MainActivity.this,UserActivity.class));
               }
               else
               {
                   if(task.getException() instanceof FirebaseAuthUserCollisionException)
                   {
                       Toast.makeText(MainActivity.this,"User Alreday existes", Toast.LENGTH_SHORT).show();
                   }
                   else
                   Toast.makeText(MainActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
               }

            }
        });

    }

    public void dothis( View v) {

        int k = v.getId();
        if (k == com.example.firebasedemo.R.id.but1)
        {
          createUser();

        }
         else if (k == com.example.firebasedemo.R.id.but2)
             {
                 Intent i = new Intent(this,LoginActivity.class);
                 startActivity(i);
                 finish();
             }

    }
}

