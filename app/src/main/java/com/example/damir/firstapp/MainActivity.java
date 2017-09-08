package com.example.damir.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button mSgninBtn;       //  The Sign in button
    private Button mSgnupBtn;       //  The Sign up button
    private EditText mUserExisting; //  Text field for an existing user
    private EditText mPassExisting; //  Text field for an existing password
    private EditText mUserNew;      //  Text field for a new user
    private EditText mPassNew;      //  Text field for a new password
    private EditText mVerPassNew;   //  Text field for verification the new password
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase     = FirebaseDatabase.getInstance().getReference();

        mSgninBtn     = (Button) findViewById(R.id.signin_button);
        mSgnupBtn     = (Button) findViewById(R.id.signup_button);

        mUserExisting = (EditText) findViewById(R.id.name_field);
        mPassExisting = (EditText) findViewById(R.id.pass_field);
        mUserNew      = (EditText) findViewById(R.id.signup_name_field);
        mPassNew      = (EditText) findViewById(R.id.signup_pass_field);
        mVerPassNew   = (EditText) findViewById(R.id.signup_veripass_field);

//        LayoutInflater myInflater = LayoutInflater.from(this);
//        View view = myInflater.inflate(R.layout.activity_main, null);
//        Toast toast = new Toast(this);
//        toast.setView(view);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setText("Invalid");
////        toast.show();

        mSgninBtn.setOnClickListener(new View.OnClickListener(){

            String userName;
            String Pass;

            @Override
            public void onClick(View v) {

                //  First we check if there was an input in both user and password field
                if (! (mUserExisting.getText().toString().equals("")) &&
                        ! (mPassExisting.getText().toString().equals("")) ) {

                    userName = mUserExisting.getText().toString();
                    Pass     = mPassExisting.getText().toString();

                    mDatabase.child("users").child(userName).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() != null) {
//                                String getPass = mDatabase.child("users").child(userName)
//                                        .child("password");
//                                String getPass = dataSnapshot
//                                        .child("password").getValue(String.class);
//                                String getPass = "";
                                String getPass = dataSnapshot.child("password").getValue().toString();

//                                Toast.makeText(MainActivity.this,
//                                        "Password = " + getPass, Toast.LENGTH_LONG).show();

                                if(! getPass.equals("" + Pass.hashCode())) {
                                    Toast.makeText(MainActivity.this,
                                            "Password incorrect\n"/* +
                                                    "You typed " + Pass.hashCode() +"\n" +
                                                    "And the pass is " + getPass*/, Toast.LENGTH_LONG).show();

                                } else {    //  Access Granted
                                    Toast.makeText(MainActivity.this,
                                            "Access granted", Toast.LENGTH_LONG).show();

                                    Intent MapActivity = new Intent(MainActivity.this, GetLocation.class);
                                    startActivity(MapActivity);

                                }

                            } else {

                                Toast.makeText(MainActivity.this,
                                        "User not found!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                } else {
                    System.out.println("Fill username and password!");
                    Toast.makeText(MainActivity.this,
                            "Fill username and password!.", Toast.LENGTH_LONG).show();


                }

//                mDatabase.child("Name").setValue("Damir");


            }
        });

        mSgnupBtn.setOnClickListener(new View.OnClickListener() {

            String userName;
            String Pass;
            String verPass;

            @Override
            public void onClick(View v) {
                //  First we check if there was an input in both user and password field
                if (! (mUserNew.getText().toString().equals("")) &&
                        ! (mPassNew.getText().toString().equals(""))&&
                        ! (mVerPassNew.getText().toString().equals("")) ) {

                    userName = mUserNew.getText().toString().toLowerCase();
                    Pass     = mPassNew.getText().toString();
                    verPass  = mVerPassNew.getText().toString();

//                    Log.i("Text", ""+mDatabase.child("users").child(userName).child("password"));

//                    Query query = mDatabase.child("users").child(userName).equalTo("");

                    //  Check if the password verification matches the password
                    if(! (Pass.equals(verPass))) {
                        Toast.makeText(MainActivity.this,
                                "The password verification failed!.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    //  Check if there is already a user with that name
                    mDatabase.child("users").child(userName).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() != null) {
                                Toast.makeText(MainActivity.this,
                                        "User already exists", Toast.LENGTH_LONG).show();
                            } else {
                                mDatabase.child("users").child(userName)
                                    .child("username").setValue(userName);
                                mDatabase.child("users").child(userName)
                                    .child("password").setValue(Pass.hashCode());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

//                    mDatabase.child("users").child(userName)
//                            .child("username").setValue(userName);
//                    mDatabase.child("users").child(userName)
//                            .child("password").setValue(Pass.hashCode());

                } else {
                    Toast.makeText(MainActivity.this,
                            "You must fill all of the fields!", Toast.LENGTH_LONG).show();
                    System.out.println("No name was provided!");
                }
            }
        });

    }
}
