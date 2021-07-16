package com.epam.finalProject.web.command;

import com.epam.finalProject.entity.User;
import com.epam.finalProject.entity.UserCart;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.defaultImpl.SingUpServiceImpl;
import com.epam.finalProject.web.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandSingUpTest {

    @Mock
    HttpServletRequest requestMock;

    @Mock
    HttpServletResponse responseMock;

    @Mock
    HttpSession sessionMock;

    @Mock
    SingUpServiceImpl singUpServiceMock;

    @InjectMocks
    private CommandSingUp commandSingUpMock;

    @BeforeEach
    public void setUp(){
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(requestMock.getParameter("username")).thenReturn("test@test.com");
        when(requestMock.getParameter("password")).thenReturn("222");
        when(requestMock.getParameter("name")).thenReturn("Test");
        when(requestMock.getParameter("surname")).thenReturn("Test");
    }

    @Test
    void whenUserNotExistTest() throws AppException {
        User user = new User();
        user.setUsername("BBB");
        UserCart userCart=new UserCart();
        when(singUpServiceMock.insertUserToDB("test@test.com","222","Test","Test","en")).thenReturn(user);
        when(requestMock.getParameter("lang")).thenReturn("en");
        when(sessionMock.getAttribute("userCart")).thenReturn(userCart);
        String result = commandSingUpMock.execute(requestMock, responseMock);
        assertEquals("controller?command=catalog", result);
    }

    @Test
    void whenUserExistTest() throws AppException {
        User user = new User();
        user.setUsername("test@test.com");
        String result = commandSingUpMock.execute(requestMock, responseMock);
        assertEquals(Path.SINGUP, result);
    }

    @Test
    void whenUsernameIsEmptyTest() throws AppException {
        when(requestMock.getParameter("username")).thenReturn("");
        String result = commandSingUpMock.execute(requestMock, responseMock);
        assertEquals(Path.SINGUP, result);
    }

    @Test
    void whenNameIsEmptyTest() throws AppException {
        User user = new User();
        user.setUsername("BBB");
        when(requestMock.getParameter("name")).thenReturn("");
        String result = commandSingUpMock.execute(requestMock, responseMock);
        assertEquals(Path.SINGUP, result);
    }
}