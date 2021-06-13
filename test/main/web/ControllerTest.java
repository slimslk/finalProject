package main.web;

import main.exception.AppException;
import main.web.command.Command;
import main.web.command.CommandContainer;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ControllerTest {

    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    Command command;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    void doPost() throws AppException {
        when(CommandContainer.getCommand(any(String.class))).thenReturn(command);
        when(command.execute(mockRequest,mockResponse)).thenReturn("/user/catalog.jsp");
    }

    @Test
    void doGet() {
    }
}