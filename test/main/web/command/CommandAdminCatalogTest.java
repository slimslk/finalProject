package main.web.command;

import main.database.ChangeGoodsDAO;
import main.entity.User;
import main.exception.AppException;
import main.web.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandAdminCatalogTest {

    @Mock
    ServletContext servletContextMock;

    @Mock
    Part part;

    @Mock
    HttpServletRequest requestMock;

    @Mock
    HttpServletResponse responseMock;

    @Mock
    HttpSession sessionMock;

    @Mock
    ChangeGoodsDAO changeGoodsDAOMock;

    @InjectMocks
    private CommandAdminCatalog commandAdminCatalogMock;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setRoleId(1);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("user")).thenReturn(user);
    }

    @Test
    void execute() throws AppException {

        when(requestMock.getParameter("action")).thenReturn(null);
        String result = commandAdminCatalogMock.execute(requestMock, responseMock);
        assertEquals(Path.ADMIN_CATALOG, result);
    }

    @Test
    void removeItem() throws AppException {
        when(requestMock.getParameter("action")).thenReturn("remove");
        when(requestMock.getParameter("goodsParamId")).thenReturn("2");
        String result = commandAdminCatalogMock.execute(requestMock, responseMock);
        assertEquals(Path.ADMIN_CATALOG, result);
    }

    @Test
    void changeItem() throws IOException, ServletException, AppException {
        when(requestMock.getParameter("action")).thenReturn("confirm");
        when(requestMock.getParameter("name")).thenReturn("Jacket");
        when(requestMock.getParameter("age")).thenReturn("Jacket");
        when(requestMock.getParameter("category")).thenReturn("Jacket");
        when(requestMock.getParameter("gender")).thenReturn("Jacket");
        when(requestMock.getParameter("size")).thenReturn("Jacket");
        when(requestMock.getParameter("style")).thenReturn("Jacket");
        when(requestMock.getPart("img")).thenReturn(part);
        when(part.getSize()).thenReturn(100L);
        when(part.getSubmittedFileName()).thenReturn("HHH.jpg");
        when(requestMock.getServletContext()).thenReturn(servletContextMock);
        when(servletContextMock.getRealPath("/")).thenReturn("img");
        when(requestMock.getParameter("itemCatalogId")).thenReturn("3");
        when(requestMock.getParameter("goodsParamId")).thenReturn("3");
        when(requestMock.getParameter("goodsId")).thenReturn("3");
        when(requestMock.getParameter("price")).thenReturn("3");
        when(requestMock.getParameter("quantity")).thenReturn("3");
        String result = commandAdminCatalogMock.execute(requestMock, responseMock);
        assertEquals(Path.ADMIN_CATALOG,result);
    }
}