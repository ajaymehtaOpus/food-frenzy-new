package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

class ExceptionsTest {

    @Test
    void handlerShouldReturnExceptionViewName() {
        Exceptions exceptions = new Exceptions();

        String result = exceptions.handler();

        assertNotNull(result);
        assertEquals("exception", result);
    }

    @Test
    void handlerShouldBeAssociatedWithInternalServerErrorStatus() throws NoSuchMethodException {
        Method method = Exceptions.class.getMethod("handler");
        ResponseStatus responseStatus = method.getAnnotation(ResponseStatus.class);

        assertNotNull(responseStatus);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseStatus.value());
    }
}