package com.weissbeerger.demo.controllers;

import com.weissbeerger.demo.dao.UserDao;
import com.weissbeerger.demo.model.User;
import com.weissbeerger.demo.services.TokenHandler;
import com.weissbeerger.demo.services.UserService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {
    @InjectMocks
    private RegistrationController registrationController;
    @Mock
    private UserDao userDao;
    @Mock
    private UserService userService;
    @Mock
    private TokenHandler tokenHandler;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


   /* @Test
    public void testGetAuthToken1() throws Exception {
        thrown.expect(NullPointerException.class);
        registrationController.getAuthToken(null);
    }*/
   /* @Test
    public void testGetAuthToken2() throws Exception {
        //if 2users are same return some token
        User user123 = new User();
        user123.setUsername("123");
        user123.setPassword("123");
        when(userService.findUserByName(user123)).thenReturn(Optional.ofNullable(user123));
        when(tokenHandler.checkMatchesPasswords(user123,user123)).thenReturn(true);
        when(tokenHandler.generateTokenId(anyInt(),anyObject())).thenReturn("someToken");
        String res = registrationController.getAuthToken(user123);
        assertEquals("someToken",res);
    }*/

    @Test
    public void testRegister() throws Exception {
        thrown.expect(NullPointerException.class);
        registrationController.register(null);
    }
    @Test
    public void testRegister2() throws Exception {
        registrationController.register(new User());
        verify(userService).addNewUser(anyObject());
    }


    @Test
    public void testGetCurrentUser() throws Exception {

    }
}