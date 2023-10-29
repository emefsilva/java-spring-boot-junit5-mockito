package io.github.emefsilva.api.resources.exceptions;

import io.github.emefsilva.api.services.exceptions.DataIntegrationViolationException;
import io.github.emefsilva.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenObjectNotFoundExceptionThenReturnResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler
                .objectNotFound(new ObjectNotFoundException("Objeto não encontrado"),
                                new MockHttpServletRequest());

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(StandardError.class, response.getBody().getClass());
        Assertions.assertEquals("Objeto não encontrado", response.getBody().getError());
        Assertions.assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void dataIntegrityViolationException() {
        ResponseEntity<StandardError> response = exceptionHandler
                .EmailIsExist(new DataIntegrationViolationException("Email já cadastrado"),
                        new MockHttpServletRequest());

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(StandardError.class, response.getBody().getClass());
        Assertions.assertEquals("Email já cadastrado", response.getBody().getError());
        Assertions.assertEquals(400, response.getBody().getStatus());
        Assertions.assertNotEquals("/user/2", response.getBody().getPath());
        Assertions.assertNotEquals(LocalDateTime.now(), response.getBody().getTimestamp());
    }
}