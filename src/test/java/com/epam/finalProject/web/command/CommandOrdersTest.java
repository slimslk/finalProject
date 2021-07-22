package com.epam.finalProject.web.command;

import com.epam.finalProject.entity.User;
import com.epam.finalProject.entity.UserOrders;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.defaultImpl.OrderServiceImpl;
import com.epam.finalProject.web.Path;
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
    OrderServiceImpl orderServiceMock;

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
        when(orderServiceMock.getUserOrdersList(any(Long.class))).thenReturn(userOrders);
        String result=commandOrdersMock.execute(requestMock,responseMock);
        assertEquals(Path.ADMIN_ORDERS,result);
    }
}