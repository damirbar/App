package com.example.damir.firstapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Damir and Leora on 9/11/2017.
 *
 * Mockito test, checks the functions of the application.
 *
 */



public class MockitoTest {

    String UserName;
    String NonValidUserName;
    String Password;
    String PasswordWrong;
    String NonValidPassword;
    MainActivity Act;

    //  Initialize of an activity and strings we use in the tests.
    @Before
    public void initialize() {
        Act = mock(MainActivity.class);
        UserName = "Leora";
        NonValidUserName = "Dami";       //  Not valid user name - to short, needed at least 5 charecters.
        Password = "123456";
        PasswordWrong = "654321";
        NonValidPassword = "1235";       //  Not valid user name - to short, needed at least 6 charecters.
    }

    //  Checks that the function returns false on a short name.
    @Test
    public void shortUserLength() {
        when(Act.registerUser(NonValidUserName, Password, Password)).thenReturn(false);
        assertFalse(Act.registerUser(NonValidUserName, Password, Password));
    }

    //  Checks that the function returns true on a valid name, valid password and the varify of the password.
    @Test
    public void newUserLength() {
        when(Act.registerUser(UserName, Password, Password)).thenReturn(true);
        assertTrue(Act.registerUser(UserName, Password, Password));
    }

    //  Checks that the function returns false on a short password.
    @Test
    public void shortPassLength() {
        when(Act.registerUser(UserName, NonValidPassword, NonValidPassword)).thenReturn(false);
        assertFalse(Act.registerUser(UserName, NonValidPassword, NonValidPassword));
    }
    //  Checks that the function returns false on a wrong verify of the password.
    @Test
    public void inCorectPassword() {
        when(Act.registerUser(UserName, Password, PasswordWrong)).thenReturn(false);
        assertFalse(Act.registerUser(UserName, Password, PasswordWrong));
    }
}
