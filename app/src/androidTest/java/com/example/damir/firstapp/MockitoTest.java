package com.example.damir.firstapp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.*;
/**
 * Created by Damir and Leora on 9/11/2017.
 */

public class MockitoTest {

    String userName;
    String Password;
    String PasswordWrong;
    DatabaseReference mDB;

    @Before
    public void initalize() {
        mDB = Mockito.mock(DatabaseReference.class);

        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);

        when(mockedFirebaseDatabase.getReference()).thenReturn(mDB);



        userName = "";
    }

    @Test
    public void userRegistrationTest() {

    }

}
