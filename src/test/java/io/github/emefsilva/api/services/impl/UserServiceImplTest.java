package io.github.emefsilva.api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.emefsilva.api.domain.User;
import io.github.emefsilva.api.domain.dto.UserDTO;
import io.github.emefsilva.api.repositories.UserRepository;
import io.github.emefsilva.api.services.exceptions.DataIntegrationViolationException;
import io.github.emefsilva.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;

@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFindByIdThenReturnUserInstance() {
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));

    }

    @Test
    void whenFindByIdThenReturnObjectNotFound() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjectNotFoundException.class, () -> userService.findById(1));
    }
    @Test

    void whenFindByIdThenReturnObjectNotFoundMessage() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
        try {
            userService.findById(1);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado",  ex.getMessage());
        }

    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    public static User setupUser() {
        return User.builder().id(1).name("Emerson").email("emerson@gmail.com").password("1234").build();
    }

    public static UserDTO setupUserDTO() {
        return UserDTO.builder().id(1).name("Emerson").email("emerson@gmail.com").password("1234").build();
    }

    public static Optional<User> setupOptional() {
        return Optional.of(User.builder().id(1).name("Emerson").email("emerson@gmail.com").password("1234").build());
    }
}