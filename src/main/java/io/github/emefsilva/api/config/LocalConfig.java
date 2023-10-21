package io.github.emefsilva.api.config;

import io.github.emefsilva.api.domain.User;
import io.github.emefsilva.api.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    private UserRepository repository;

    public LocalConfig(UserRepository repository) {
        this.repository = repository;
    }

    @Bean
    public void startDB() {
        User u1 = new User(null, "Emerson", "emerson@gmail.com", "123");
        User u2 = new User(null, "João", "joão@gmail.com", "123");
        User u3 = new User(null, "Fernanda", "fernanda@gmail.com", "123");

        repository.saveAll(List.of(u1,u2));
    }
}
