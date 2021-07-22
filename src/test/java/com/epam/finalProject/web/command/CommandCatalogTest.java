package com.epam.finalProject.web.command;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandCatalogTest {

    @Mock
    HttpServletRequest requestMock;

    @Mock
    HttpServletResponse responseMock;

    @Mock
    HttpSession sessionMock;

    @Mock
    CatalogServiceImpl catalogServiceMock;

    @InjectMocks
    CommandCatalog commandCatalogMock;

    @Test
    void execute() throws AppException {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(catalogServiceMock.getCountOfGoods()).thenReturn(10);
        String result = commandCatalogMock.execute(requestMock,responseMock);
        assertEquals(Path.CATALOG,result);
    }
}