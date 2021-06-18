package main.web.command;

import main.database.ChangeGoodsDAO;
import main.database.UserDAO;
import main.database.UserInfoDAO;
import main.entity.User;
import main.entity.UserCart;
import main.exception.AppException;
import main.web.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

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
    UserDAO userDAOMock;

    @Mock
    UserInfoDAO userInfoDAOMock;

    @InjectMocks
    private CommandSingUp commandSingUpMock;

    @BeforeEach
    public void setUp(){
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(requestMock.getParameter("username")).thenReturn("lili@gmai.com");
        when(requestMock.getParameter("password")).thenReturn("777");
        when(requestMock.getParameter("name")).thenReturn("Lili");
        when(requestMock.getParameter("surname")).thenReturn("Ya");
    }

    @Test
    void whenUserNotExistTest() throws AppException {
        User user = new User();
        user.setUsername("BBB");
        UserCart userCart=new UserCart();
        when(userDAOMock.getUserByUsername("lili@gmai.com")).thenReturn(user);
        when(requestMock.getParameter("lang")).thenReturn("en");
        when(sessionMock.getAttribute("userCart")).thenReturn(userCart);
        String result = commandSingUpMock.execute(requestMock, responseMock);
        assertEquals("controller?command=catalog", result);
    }

    @Test
    void whenUserExistTest() throws AppException {
        User user = new User();
        user.setUsername("lili@gmai.com");
        when(userDAOMock.getUserByUsername("lili@gmai.com")).thenReturn(user);
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
        when(userDAOMock.getUserByUsername("lili@gmai.com")).thenReturn(user);
        String result = commandSingUpMock.execute(requestMock, responseMock);
        assertEquals(Path.SINGUP, result);
    }


}