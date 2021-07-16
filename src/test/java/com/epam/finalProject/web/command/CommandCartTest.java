package com.epam.finalProject.web.command;

import com.epam.finalProject.entity.CatalogItem;
import com.epam.finalProject.entity.UserCart;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.defaultImpl.CatalogServiceImpl;
import com.epam.finalProject.web.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandCartTest {
    @Mock
    HttpServletRequest requestMock;

    @Mock
    HttpServletResponse responseMock;

    @Mock
    HttpSession sessionMock;

    @Mock
    CatalogServiceImpl catalogServiceMock;

    @InjectMocks
    CommandCart commandCartMock;

    @Test
    void execute() throws AppException {
        UserCart userCart = new UserCart();
        Map<Long, Integer> map = new HashMap<>();
        List<Long> listId=new ArrayList<>();
        listId.add(0L);
        listId.add(1L);
        map.put(0L, 1);
        map.put(1L, 2);
        userCart.setGoodsId(map);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("userCart")).thenReturn(userCart);
        when(catalogServiceMock.getListOfItems(listId)).thenReturn(new ArrayList<>());
        String result = commandCartMock.execute(requestMock, responseMock);
        assertEquals(Path.CART, result);
    }
}