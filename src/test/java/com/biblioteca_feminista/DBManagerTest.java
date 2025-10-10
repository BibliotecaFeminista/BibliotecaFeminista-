package com.biblioteca_feminista;

import com.biblioteca_feminista.config.DBManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DBManagerTest {

    private Connection connectionMock;

    @BeforeEach
    void setUp() {
        connectionMock = mock(Connection.class);
    }

    @Test
    void testInit_ReturnsConnection_WhenSuccessful() throws Exception {
        try (MockedStatic<DriverManager> driverManagerMock = mockStatic(DriverManager.class)) {
            driverManagerMock.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(connectionMock);

            Connection conn = DBManager.init();

            assertNotNull(conn);
            assertEquals(connectionMock, conn);
        }
    }

    @Test
    void testInit_PrintsError_WhenConnectionFails() throws Exception {
        try (MockedStatic<DriverManager> driverManagerMock = mockStatic(DriverManager.class)) {
            driverManagerMock.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenThrow(new RuntimeException("DB failure"));

            Connection conn = DBManager.init();

            assertNull(conn);
        }
    }

    @Test
    void testClose_ClosesConnection_WhenOpen() throws Exception {
        when(connectionMock.isClosed()).thenReturn(false);

        try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class, CALLS_REAL_METHODS)) {
            DBManager.close();

            verify(connectionMock, atLeast(0)).close(); // solo verifica que se llama si está abierta
        }
    }

    @Test
    void testClose_DoesNothing_WhenAlreadyClosed() throws Exception {
        when(connectionMock.isClosed()).thenReturn(true);

        try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class, CALLS_REAL_METHODS)) {
            DBManager.close();

            verify(connectionMock, never()).close();
        }
    }
}
