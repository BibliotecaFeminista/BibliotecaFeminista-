package com.biblioteca_feminista;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppMockitoTest {

    interface Service {
        String greet();
    }

    @Test
    void mockitoWorks() {
        Service svc = mock(Service.class);
        when(svc.greet()).thenReturn("oi, Dani!");
        assertEquals("oi, Dani!", svc.greet());
        verify(svc).greet();
    }
}
