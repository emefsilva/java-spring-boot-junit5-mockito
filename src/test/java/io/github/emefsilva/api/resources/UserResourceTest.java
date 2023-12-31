package io.github.emefsilva.api.resources;

import io.github.emefsilva.api.domain.User;
import io.github.emefsilva.api.domain.dto.UserDTO;
import io.github.emefsilva.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserResourceTest {


    private static final Integer ID      = 1;
    private static final Integer INDEX   = 0;
    private static final String NAME     = "Valdir";
    private static final String EMAIL    = "valdir@mail.com";
    private static final String PASSWORD = "123";

    @Autowired
    private UserResource userResource;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        Mockito.when(userService.findById(Mockito.anyInt())).thenReturn(user);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userResource.findById(ID);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals( ResponseEntity.class,response.getClass());
        Assertions.assertEquals(UserDTO.class, response.getBody().getClass());


        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(NAME, response.getBody().getName());
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());
        Assertions.assertEquals(PASSWORD, response.getBody().getPassword());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenFindAllThenReturnAListUsers() {
        Mockito.when(userService.findAll()).thenReturn(List.of(user));
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);
        ResponseEntity<List<UserDTO>> response = userResource.findAll();
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(1, response.getBody().size());
        Assertions.assertEquals(ArrayList.class, response.getBody().getClass());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(userService, Mockito.times(1)).findAll();
        Mockito.verify(modelMapper, Mockito.times(1)).map(Mockito.any(), Mockito.any());

        Assertions.assertEquals(ID, response.getBody().get(INDEX).getId());
        Assertions.assertEquals(NAME, response.getBody().get(INDEX).getName());
        Assertions.assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        Assertions.assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
    }

    @Test
    void whenCreateUserThenReturnSuccessAndStatus201() {
        Mockito.when(userService.create(Mockito.any())).thenReturn(user);
        ResponseEntity<UserDTO> response = userResource.createUser(userDTO);

        Assertions.assertNotNull(response.getHeaders().get("Location"));
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    void whenUpdateUserThenReturnSuccessAndStatus204() {
        Mockito.when(userService.findById(ID)).thenReturn(user);
        Mockito.when(userService.update(Mockito.any(), Mockito.anyInt())).thenReturn(user);
        Mockito.when(modelMapper.map(Mockito.any(),Mockito.any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userResource.updateUser(userDTO, ID);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(UserDTO.class, response.getBody().getClass());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());

        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(NAME, response.getBody().getName());
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());
    }
    @Test
    void WhenDeleteUserThenReturnSuccessAndStatusCode204() {

        Mockito.doNothing().when(userService).delete(Mockito.anyInt());

        ResponseEntity<UserDTO> response = userResource.deleteUser(ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        Mockito.verify(userService, Mockito.times(1)).delete(Mockito.anyInt());
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL,  PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}