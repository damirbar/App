package com.example.damir.firstapp;

/*----------------------------------------------------------------------------------------------*
 *   Copyright (c) 2017, Leora Schmerler and Damir Bar. All rights reserved.                    *
 *   Do not alter or remove copyright notices or this header.                                   *
 *                                                                                              *
 *   The following code is free to use. You are allowed to modify any part of it.               *
------------------------------------------------------------------------------------------------*/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 *  Sep - 14 - 2017
 *
 *  <h1>Software Structure - final exercise</h1>
 *  <h2>
 *  This activity is the main activity. It has options to either sign in or sign up.
 *  Once a user exists and signs in, it will transfer him/her to the activity GetLocation.
 *  </h2>
 *
 *  <p>Have fun</p>
 */

public class MainActivity extends AppCompatActivity {

    private Button mSgninBtn;       //  The Sign in button
    private Button mSgnupBtn;       //  The Sign up button
    private EditText mUserExisting; //  Text field for an existing user
    private EditText mPassExisting; //  Text field for an existing password
    private EditText mUserNew;      //  Text field for a new user
    private EditText mPassNew;      //  Text field for a new password
    private EditText mVerPassNew;   //  Text field for verification the new password

    private DatabaseReference mDatabase;
    private boolean RegisterOnce;   //  A flag to let a user register once.


    /**
     *  The typical onCreate function. Initializes buttons, edit-texts and out database.
     *
     *  This onCreate also includes an implementation of the buttons' OnClickListeners.
     *
     *  @param savedInstanceState
     */

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

        RegisterOnce = true;

        mSgninBtn.setOnClickListener(new View.OnClickListener(){

            String userName;
            String Pass;

            @Override
            public void onClick(View v) {

                //  First we check if there was an input in both user and password field
                if (! (mUserExisting.getText().toString().equals("")) &&
                        ! (mPassExisting.getText().toString().equals("")) ) {

                    userName = mUserExisting.getText().toString().toLowerCase();
                    Pass     = mPassExisting.getText().toString().toLowerCase();

                    getUser(userName, Pass);


                } else {
                    String message = String.format(MainActivity.this.getResources()
                            .getString(R.string.fill_user_pass));

                    Toast.makeText(MainActivity.this, message,
                            Toast.LENGTH_LONG).show();

                }

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

                    registerUser(userName, Pass, verPass);

                } else {    //  The user didn't fill all of the fields.
                    String message = String.format(MainActivity.this.getResources()
                            .getString(R.string.fill_all_fields));

                    Toast.makeText(MainActivity.this, message,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     *  This method is for fetching an existing user.
     *
     *  If the user doesn't exist, or the password is incorrect it returns false.
     *
     *  If the user exists it returns true and performs an intent to the GetLocation
     *  activity.
     *
     * @param userName
     * @param Pass
     * @return true --> success. false --> failure.
     */

    public boolean getUser(final String userName, final String Pass) {

        final boolean[] flag = {true};

        mDatabase.child("users").child(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {

                    String getPass = dataSnapshot.child("password").getValue().toString();

                    if(! getPass.equals("" + Pass.hashCode())) {
                        String message = String.format(MainActivity.this.getResources()
                                .getString(R.string.pass_incorrect));

                        Toast.makeText(MainActivity.this, message,
                                Toast.LENGTH_LONG).show();

                        flag[0] = false;

                    } else {    //  Access Granted
                        String message = String.format(MainActivity.this.getResources()
                                .getString(R.string.access_granted));

                        Toast.makeText(MainActivity.this, message,
                                Toast.LENGTH_LONG).show();

                        mUserExisting.setText(null);
                        mPassExisting.setText(null);


                        //  There is a need for keeping the user name information in order to
                        //  later obtain his item list.
                        Intent MapActivity = new Intent(MainActivity.this, GetLocation.class);
                        MapActivity.putExtra("name", userName);
                        startActivity(MapActivity);

                        flag[0] = true;
                    }

                } else {

                    String message = String.format(MainActivity.this.getResources()
                            .getString(R.string.user_not_found));

                    Toast.makeText(MainActivity.this, message,
                            Toast.LENGTH_LONG).show();

                    flag[0] = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return flag[0];
    }

    /**
     *  This method is for registering a new user.
     *
     *  If the user doesn't exist, the user name is at least 5 characters long and
     *  the password is at least 6 characters long, there will a checking whether
     *  the user exists. If it doesn't, the method will register it and return true.
     *  If not, it will not register the user and will return false.
     *
     * @param userName
     * @param Pass
     * @param verPass
     * @return  true --> success. false --> failure.
     */

    public boolean registerUser(final String userName, final String Pass, String verPass) {

        //  The user name must be at least 5 characters long.
        if(userName.length() < 5) {
            String message = String.format(MainActivity.this.getResources()
                    .getString(R.string.user_length_min));

            Toast.makeText(MainActivity.this, message,
                    Toast.LENGTH_LONG).show();

            return false;
        }

        //  The password must be at least 6 characters long.
        if(Pass.length() < 6) {
            String message = String.format(MainActivity.this.getResources()
                    .getString(R.string.pass_length_min));

            Toast.makeText(MainActivity.this, message,
                    Toast.LENGTH_LONG).show();

            return false;
        }

        //  Checking if the password verification matches the password.
        if(! (Pass.equals(verPass))) {
            String message = String.format(MainActivity.this.getResources()
                    .getString(R.string.pass_ver_failed));

            Toast.makeText(MainActivity.this, message,
                    Toast.LENGTH_LONG).show();

            return false;
        }

        //  Check if there is already a user with that name
        mDatabase.child("users").child(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!RegisterOnce) {

                }
                if (dataSnapshot.exists() && RegisterOnce) {
                    String message = String.format(MainActivity.this.getResources()
                            .getString(R.string.user_exists));

                    Toast.makeText(MainActivity.this, message,
                            Toast.LENGTH_LONG).show();

                    return;

                } else {
                    RegisterOnce = false;
                    mDatabase.child("users").child(userName)
                            .child("username").setValue(userName);
                    mDatabase.child("users").child(userName)
                            .child("password").setValue(Pass.hashCode());

                    String message = String.format(MainActivity.this.getResources()
                            .getString(R.string.welcome), userName);

                    Toast.makeText(MainActivity.this, message,
                            Toast.LENGTH_LONG).show();

                    mUserNew.setText(null);
                    mPassNew.setText(null);
                    mVerPassNew.setText(null);

                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //  Pass. There is no option to cancel.
            }
        });

        return true;

    }

}
