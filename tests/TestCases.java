package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import server.UserServiceImpl;

public class TestCases {
    @Test
    public void testValidarUsuario() {
        UserServiceImpl userService = new UserServiceImpl();
        assertTrue(userService.validarUsuario("user1"));
        assertFalse(userService.validarUsuario("userX"));
    }
}
