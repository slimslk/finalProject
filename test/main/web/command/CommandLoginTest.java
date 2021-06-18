package main.web.command;

import main.database.UserDAO;
import main.entity.User;
import main.entity.UserCart;
import main.exception.AppException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CommandLoginTest {

    @Test
    public void shouldReturnCatalogPage() throws AppException {
        User user = new User();
        user.setUsername("slim.slk@gmail.com");
        user.setPassword("777");
        HttpServletRequest requestMock=mock(HttpServletRequest.class);
        HttpServletResponse responseMock=mock(HttpServletResponse.class);
        HttpSession sessionMock=mock(HttpSession.class);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(requestMock.getParameter("username")).thenReturn("slim.slk@gmail.com");
        when(requestMock.getParameter("password")).thenReturn("777");
        UserDAO userDAO = mock(UserDAO.class);
        Command commandLoginMock = new CommandLogin(userDAO);
        when(userDAO.getUserByUsername(any(String.class))).thenReturn(user);
        doNothing().when(sessionMock).setAttribute(any(String.class), eq(user));
        UserCart userCart = new UserCart();
        when(sessionMock.getAttribute(any(String.class))).thenReturn(userCart);
        String result = commandLoginMock.execute(requestMock, responseMock);
        System.out.println("RESULT: " + result);
        assertEquals("null/controller?command=orders&action=orders", result);
    }
}