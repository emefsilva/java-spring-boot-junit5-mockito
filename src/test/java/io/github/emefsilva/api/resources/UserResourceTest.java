package io.github.emefsilva.api.resources;

import io.github.emefsilva.api.domain.User;
import io.github.emefsilva.api.domain.dto.UserDTO;
import io.github.emefsilva.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

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
    }

    @Test
    void findAll() {
    }

    @Test
    void createUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL,  PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}