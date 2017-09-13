package com.example.damir.firstapp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import static org.powermock.api.mockito.PowerMockito;
import static org.mockito.Mockito.*;
/**
 * Created by Damir and Leora on 9/11/2017.
 */



public class MockitoTest {

    String UserName;
    String NonValidUserName;
    String Password;
    String PasswordWrong;
    String NonValidPassword;
    MainActivity Act;

    @Before
    public void initalize() {
        Act = mock(MainActivity.class);
        UserName = "Leora";
        NonValidUserName = "Dami";
        Password = "123456";
        PasswordWrong = "654321";
        NonValidPassword = "1235";
    }

    @Test
    public void shortUserLength() {
        when(Act.registerUser(NonValidUserName, Password, Password)).thenReturn(true);
        assertTrue(Act.registerUser(NonValidUserName, Password, Password));
    }

    @Test
    public void newUserLength() {
        when(Act.registerUser(UserName, Password, Password)).thenReturn(true);
        assertTrue(Act.registerUser(UserName, Password, Password));
    }

    @Test
    public void shortPassLength() {
        when(Act.registerUser(UserName, NonValidPassword, NonValidPassword)).thenReturn(true);
        assertTrue(Act.registerUser(UserName, NonValidPassword, NonValidPassword));
    }
    @Test
    public void inCorectPassword() {
        when(Act.registerUser(UserName, Password, PasswordWrong)).thenReturn(true);
        assertTrue(Act.registerUser(UserName, Password, PasswordWrong));
    }
}
