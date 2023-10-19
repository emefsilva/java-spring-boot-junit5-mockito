package io.github.emefsilva.api.services;

import io.github.emefsilva.api.domain.User;

import java.util.List;

public interface UserService {

    User findById(Integer id);

    List<User> findAll();
}
