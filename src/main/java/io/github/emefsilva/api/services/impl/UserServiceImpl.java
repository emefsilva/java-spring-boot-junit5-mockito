package io.github.emefsilva.api.services.impl;

import io.github.emefsilva.api.domain.User;
import io.github.emefsilva.api.repositories.UserRepository;
import io.github.emefsilva.api.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Integer id) {
        Optional<User> obj = userRepository.findById(id);

        return obj.orElse(null);
    }
}
