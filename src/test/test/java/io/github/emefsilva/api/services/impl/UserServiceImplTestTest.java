package io.github.emefsilva.api.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyInt;

import io.github.emefsilva.api.domain.User;
import io.github.emefsilva.api.domain.dto.UserDTO;
import io.github.emefsilva.api.repositories.UserRepository;
import io.github.emefsilva.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userServiceImpl;

    public static final Integer ID = 1;
    public static final String NAME = "Emerson";
    public static final String EMAIL = "emerson@gmail.com";
    public static final String PASSWORD = "1234";

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
    void testFindByIdWhenValidIdThenReturnUserInstance() {
        User user = new User();
        user.setId(ID);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        when(userRepository.findById(ID)).thenReturn(Optional.of(user));

        User result = userService.findById(ID);

        assertEquals(user, result);

        verify(userRepository, times(1)).findById(ID);
    }

    @Test
    void testFindByIdWhenInvalidIdThenThrowObjectNotFoundException() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> userService.findById(ID));

        verify(userRepository, times(1)).findById(ID);
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
        return User.builder().id(ID).name("Emerson").email("emerson@gmail.com").password("1234").build();
    }

    public static UserDTO setupUserDTO() {
        return UserDTO.builder().id(ID).name("Emerson").email("emerson@gmail.com").password("1234").build();
    }

    public static Optional<User> setupOptional() {
        return Optional.of(User.builder().id(ID).name("Emerson").email("emerson@gmail.com").password("1234").build());
    }
}
