package io.github.emefsilva.api.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import io.github.emefsilva.api.domain.User;
import io.github.emefsilva.api.domain.dto.UserDTO;
import io.github.emefsilva.api.repositories.UserRepository;
import io.github.emefsilva.api.services.exceptions.DataIntegrationViolationException;
import io.github.emefsilva.api.services.exceptions.ObjectNotFoundException;
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


import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final Integer ID      = 1;
    private static final Integer INDEX   = 0;
    private static final String NAME     = "Valdir";
    private static final String EMAIL    = "valdir@mail.com";
    private static final String PASSWORD = "123";

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ModelMapper modelMapper;


    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnUserInstance() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));

        var response = userService.findById(ID);

        Assertions.assertNotNull(response);
    }

    @Test
    void whenFindByIdThenReturnObjectNotFound() {
        Mockito.lenient().when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjectNotFoundException.class, () -> userService.findById(1));
    }
    @Test

    void whenFindByIdThenReturnObjectNotFoundMessage() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
        try {
            userService.findById(Mockito.anyInt());
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado",  ex.getMessage());
        }

    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> response = userService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(INDEX).getClass());
        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(PASSWORD, response.get(INDEX).getPassword());
    }
    @Test
    void whenCreateThenReturnSuccess() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        User response = userService.create(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnDataIntegrityViolationException() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);
        try {
            optionalUser.get().setId(2);
            userService.create(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegrationViolationException.class, ex.getClass());
            assertEquals("Email já cadastrado", ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        User response = userService.update(userDTO, ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnDataIntegrityViolationException() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);
        try {
            optionalUser.get().setId(2);
            userService.update(userDTO, ID);
        } catch (Exception ex) {
            assertEquals(DataIntegrationViolationException.class, ex.getClass());
            assertEquals("Email já cadastrado", ex.getMessage());
        }
    }

    @Test
    void whenDeleteSuccess() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRepository).deleteById(Mockito.anyInt());
        userService.delete(ID);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(ID);
    }

    @Test
    void whenDeleteObjectNotFoundException() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
        try {
            userService.delete(ID);
        }catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado",  ex.getMessage());
        }

    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL,  PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}