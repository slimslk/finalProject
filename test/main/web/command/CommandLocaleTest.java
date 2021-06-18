package main.web.command;

import main.database.CatalogDAO;
import main.database.UserDAO;
import main.entity.User;
import main.exception.AppException;
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
class CommandLocaleTest {

    @Mock
    HttpServletRequest requestMock;

    @Mock
    HttpServletResponse responseMock;

    @Mock
    HttpSession sessionMock;

    @Mock
    UserDAO userDAOMock;

    @InjectMocks
    CommandLocale commandLocaleMock;

    @Test
    void execute() throws AppException {
        User user=new User();
        user.setUsername("A@A.com");
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("user")).thenReturn(user);
        when(requestMock.getParameter("lang")).thenReturn("ru");
        String result = commandLocaleMock.execute(requestMock, responseMock);
        assertNull(result);
    }
}