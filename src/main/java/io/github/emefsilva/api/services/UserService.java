package io.github.emefsilva.api.services;

import io.github.emefsilva.api.domain.User;

public interface UserService {

    User findById(Integer id);
}
