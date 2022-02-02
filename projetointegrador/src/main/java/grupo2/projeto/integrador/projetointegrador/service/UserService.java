package grupo2.projeto.integrador.projetointegrador.service;

import java.nio.charset.Charset;
import java.util.Optional;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import grupo2.projeto.integrador.projetointegrador.model.Usuario;
import grupo2.projeto.integrador.projetointegrador.repository.UsuarioRepository;

@Service
public class UserService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario SaveUser (Usuario user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String passwordEncoder = encoder.encode(user.getPassword());
        user.setPassword(passwordEncoder);

        return repository.save(user);

    }

    public Optional<UserLogin> login (Optional<UserLogin> user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder;
        Optional<Usuario> user = repository.findByUser(user.get().getUsuario());

        if (user.isPresent()) {
            if (encoder.matches(user.get().getPassword(), user.get().getPassword())) {

                String auth = user.get().getUsuario() + ":" + user.get().getPassword();
                byte[] encoderAuth = Base64.enconderBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(enconderAuth);

                user.get().setToken(autoHeader);
                user.get().setName(usuario.get().getName());
            }
        }
        return null;
    }

}