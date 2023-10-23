package io.github.emefsilva.api.services.impl;

import io.github.emefsilva.api.domain.User;
import io.github.emefsilva.api.domain.dto.UserDTO;
import io.github.emefsilva.api.repositories.UserRepository;
import io.github.emefsilva.api.services.UserService;
import io.github.emefsilva.api.services.exceptions.DataIntegrationViolationException;
import io.github.emefsilva.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public User findById(Integer id) {
        Optional<User> obj = userRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(UserDTO userDTO) {
        findByEmail(userDTO);
        return userRepository.save(mapper.map(userDTO, User.class));
    }

    @Override
    public User update(UserDTO userDTO, Integer id) {
        findByEmail(userDTO);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());

        return userRepository.save(existingUser);
    }

    @Override
    public void delete(Integer id) {
         findById(id);
        userRepository.deleteById(id);
    }

    private void findByEmail(UserDTO userDTO) {
        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
       if(user.isPresent()) {
           throw new DataIntegrationViolationException("Email já cadastrado");
       }
    }
}
