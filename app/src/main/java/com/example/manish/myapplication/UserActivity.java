package com.example.manish.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {

    FirebaseAuth userauth;

    DatabaseReference child = FirebaseDatabase.getInstance().getReference().child("Users");

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.firebasedemo.R.layout.activity_user);

        tv = findViewById(com.example.firebasedemo.R.id.tvuser);

        userauth = FirebaseAuth.getInstance();

        FirebaseUser user  = userauth.getCurrentUser();

        if(user != null)
        {
            displayName();
        }
    }
    void displayName()
    {

        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               DataSnapshot ds = dataSnapshot.child(userauth.getCurrentUser().getUid());

                Person p = ds.getValue(Person.class);

                String name = p.getName();

                tv.setText("\n"+ name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void doadd(View view) {

        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(com.example.firebasedemo.R.layout.my_layout,null);

        final EditText type = v.findViewById(com.example.firebasedemo.R.id.tv3);

        ad.setView(v);

        ad.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String info = type.getText().toString();

                String userid = userauth.getCurrentUser().getUid();

                child.child(userid).child("name").setValue(tv.getText().toString()+"\n"+ info);
                //tv.setText(tv.getText()+"\n"+info);

                dialogInterface.cancel();
            }
        });
        ad.show();

    }

    public void dologout(View view) {

        userauth.signOut();
        finish();
        startActivity(new Intent(UserActivity.this,MainActivity.class));
    }
}
