package app.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import app.model.UserSecurityLogin;
import app.model.User;
import app.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User saveUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String passwordEncoder = encoder.encode(user.getPassword());
        user.setPassword(passwordEncoder);

        return repository.save(user);

    }

    public Optional<UserSecurityLogin> login(Optional<UserSecurityLogin> user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<User> usuario = repository.findByEmail(user.get().getEmail());

        if (usuario.isPresent()) {
            if (encoder.matches(user.get().getPassword(), usuario.get().getPassword())) {

                String auth = user.get().getEmail() + ":" + user.get().getPassword();
                byte[] encoderAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encoderAuth);

                user.get().setToken(authHeader);
                user.get().setFullName(usuario.get().getFullName());
                user.get().setId(usuario.get().getId());
                user.get().setEmail(usuario.get().getEmail());
                user.get().setPicture(usuario.get().getPicture());
                user.get().setType(usuario.get().getType());

                return user;                
            }  
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha Inválida");
        }
        return null;
    }
}