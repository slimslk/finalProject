package main.web.command;

import main.database.OrderDAO;
import main.database.OrderListDAO;
import main.entity.User;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandAddOrderTest {

    @Mock
    OrderDAO orderDAOMock;

    @Mock
    OrderListDAO orderListDAOMock;

    @InjectMocks
    private CommandAddOrder commandAddOrderMock;

    @Test
    void shouldReturnToUserOrders() throws AppException {
        User user=new User();
        user.setId(2L);
        String[] goodsId={"1","2"};
        String[] goodsQuantity={"2","2"};
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);
        HttpSession sessionMock = mock(HttpSession.class);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("user")).thenReturn(user);
        when(requestMock.getParameterValues("goodsId")).thenReturn(goodsId);
        when(requestMock.getParameterValues("quantity")).thenReturn(goodsQuantity);
        when(orderDAOMock.getMaxId()).thenReturn(3L);
        String result = commandAddOrderMock.execute(requestMock, responseMock);
        assertEquals(Path.REDIRECT_TO_USER_ORDERS, result);
    }
}