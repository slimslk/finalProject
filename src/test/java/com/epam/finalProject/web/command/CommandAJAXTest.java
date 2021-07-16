package com.epam.finalProject.web.command;

import com.epam.finalProject.database.CatalogDAO;
import com.epam.finalProject.entity.CatalogItem;
import com.epam.finalProject.entity.User;
import com.epam.finalProject.entity.UserCart;
import com.epam.finalProject.exception.AppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandAJAXTest {

    @Mock
    PrintWriter outMock;

    @Mock
    HttpServletRequest requestMock;

    @Mock
    HttpServletResponse responseMock;

    @Mock
    HttpSession sessionMock;

    @Mock
    CatalogDAO catalogDAOMock;

    @InjectMocks
    private CommandAJAX commandAJAXMock;

    @BeforeEach
    public void setUp() throws IOException {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(responseMock.getWriter()).thenReturn(outMock);
    }


    @Test
    void filterGoodsTest() throws AppException {
        User user = new User();
        user.setRoleId(2);
        Map<Integer, Object> map = new HashMap<>();
        List<CatalogItem> catalogItemList = new ArrayList<>();
        catalogItemList.add(new CatalogItem());
        catalogItemList.add(new CatalogItem());
        map.put(0, 3);
        map.put(1, catalogItemList);
        when(requestMock.getParameter("do")).thenReturn("filter");
        when(sessionMock.getAttribute("user")).thenReturn(user);
        when(requestMock.getParameterValues("param")).thenReturn(new String[]{"size", "age"});
        when(requestMock.getParameter("sort")).thenReturn("addDate");
        when(requestMock.getParameter("direction")).thenReturn("asc");
        when(requestMock.getParameter("start")).thenReturn("0");
        when(catalogDAOMock.getListOfSortedItems(new String[]{"size", "age"}, "adddate", "asc", 0)).thenReturn(map);
        String result = commandAJAXMock.execute(requestMock, responseMock);
        assertNull(result);
    }

    @Test
    void addToCartTest() throws AppException {
        UserCart userCart = new UserCart();
        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setQuantity(3);
        when(requestMock.getParameter("do")).thenReturn("add");
        when(sessionMock.getAttribute("inCartCount")).thenReturn(2);
        when(requestMock.getParameter("goodsId")).thenReturn("2");
        when(requestMock.getParameter("quantity")).thenReturn("2");
        when(sessionMock.getAttribute("userCart")).thenReturn(userCart);
        when(catalogDAOMock.getItemByGoodsId(2L)).thenReturn(catalogItem);
        String result = commandAJAXMock.execute(requestMock, responseMock);
        assertNull(result);
    }
}