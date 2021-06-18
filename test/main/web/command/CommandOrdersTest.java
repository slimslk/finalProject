package main.web.command;

import main.database.OrderListDAO;
import main.database.UserDAO;
import main.entity.User;
import main.entity.UserOrders;
import main.exception.AppException;
import main.web.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandOrdersTest {

    @Mock
    HttpServletRequest requestMock;

    @Mock
    HttpServletResponse responseMock;

    @Mock
    HttpSession sessionMock;

    @Mock
    OrderListDAO orderListDAOMock;

    @InjectMocks
    private CommandOrders commandOrdersMock;

    @Test
    void changeOrderStatusTest() throws AppException {
        User user=new User();
        user.setRoleId(2);
        UserOrders userOrders=new UserOrders();
        when(requestMock.getParameter("action")).thenReturn("orders");
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("user")).thenReturn(user);
        when(orderListDAOMock.getOrderListByUserId(any(Long.class))).thenReturn(userOrders);
        String result=commandOrdersMock.execute(requestMock,responseMock);
        assertEquals(Path.ADMIN_ORDERS,result);
    }
}