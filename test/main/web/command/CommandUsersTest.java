package main.web.command;

import main.database.UserDAO;
import main.database.UserInfoDAO;
import main.entity.User;
import main.exception.AppException;
import main.web.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandUsersTest {

    @Mock
    HttpServletRequest requestMock;

    @Mock
    HttpServletResponse responseMock;

    @Mock
    HttpSession sessionMock;

    @Mock
    UserDAO userDAOMock;

    @InjectMocks
    private CommandUsers commandUsersMock;

    @Test
    void changeUserStatus() throws AppException {
        User user =new User();
        user.setRoleId(2);
        when(requestMock.getParameter("action")).thenReturn("users");
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("user")).thenReturn(user);
        when(requestMock.getParameter("action")).thenReturn("update");
        when(requestMock.getParameter("param")).thenReturn("lili");
        when(requestMock.getParameter("status")).thenReturn("2");
        String result=commandUsersMock.execute(requestMock,responseMock);
        assertEquals(Path.ADMIN_USERS,result);
    }

}