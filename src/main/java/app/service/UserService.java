package app.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.model.UserSecurityLogin;
import app.model.Usuario;
import app.repository.UsuarioRepository;

@Service
public class UserService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario saveUser(Usuario user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String passwordEncoder = encoder.encode(user.getPassword());
        user.setPassword(passwordEncoder);

        return repository.save(user);

    }

    public Optional<UserSecurityLogin> login(Optional<UserSecurityLogin> user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<Usuario> usuario = repository.findByEmail(user.get().getEmail());

        if (usuario.isPresent()) {
            if (encoder.matches(user.get().getPassword(), usuario.get().getPassword())) {

                String auth = user.get().getEmail() + ":" + user.get().getPassword();
                byte[] encoderAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encoderAuth);

                user.get().setToken(authHeader);
                user.get().setFullName(usuario.get().getFullName());

                return user;
            }
        }
        return null;
    }

}