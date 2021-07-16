package com.epam.finalProject.web.command;

import com.epam.finalProject.entity.User;
import com.epam.finalProject.entity.UserCart;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.defaultImpl.LoginServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandLoginTest {

    @Mock
    HttpServletRequest requestMock;

    @Mock
    HttpServletResponse responseMock;

    @Mock
    HttpSession sessionMock;

    @Mock
    LoginServiceImpl loginServiceMock;

    @InjectMocks
    CommandLogin commandLoginMock;

    @Test
    public void shouldReturnCatalogPage() throws AppException {
        User user = new User();
        user.setUsername("slim.slk@gmail.com");
        user.setPassword("777");
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(requestMock.getParameter("username")).thenReturn("slim.slk@gmail.com");
        when(requestMock.getParameter("password")).thenReturn("777");
        when(loginServiceMock.getUserByUsername(any(String.class),any(String.class))).thenReturn(user);
        UserCart userCart = new UserCart();
        when(sessionMock.getAttribute(any(String.class))).thenReturn(userCart);
        String result = commandLoginMock.execute(requestMock, responseMock);
        assertEquals("null/controller?command=orders&action=orders", result);
    }
}