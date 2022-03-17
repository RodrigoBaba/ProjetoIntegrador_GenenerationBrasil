package app.service;

import app.model.UserSecurityLogin;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;
import java.util.Optional;

import app.model.User;
import app.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Optional<User> saveUser(User user) {
    	
    	if(repository.findByEmail(user.getEmail()).isPresent()) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já existente!");
    	}
    	
    	
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String passwordEncoder = encoder.encode(user.getPassword());
        user.setPassword(passwordEncoder);

        return Optional.of(repository.save(user));

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
    
    public Optional<User> updateBio(User user) {
        Optional<User> usuario = repository.findByEmail(user.getEmail());

        if (usuario.isPresent()) {
    
            usuario.get().setAutoBiography(user.getAutoBiography());           

            return Optional.of(repository.save(usuario.get())); 
            	  
        }
        return null;
    }
    
    public Optional<User> updateUser(User form){
		if(repository.findById(form.getId()).isPresent()) {

			Optional<User> userDB = repository.findByEmail(form.getEmail());
			if(userDB.isPresent()) {
				if(userDB.get().getId() != form.getId()) 
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já exise", null);									
			}
			userDB.get().setFullName(form.getFullName());
			userDB.get().setPassword(encryptedPassword(form.getPassword()));
			userDB.get().setPicture(form.getPicture());
			userDB.get().setAutoBiography(form.getAutoBiography());
			userDB.get().setPhoneNumber(form.getPhoneNumber());
			userDB.get().setAge(form.getAge());
			userDB.get().setLinkedin(form.getLinkedin());
			userDB.get().setGitHub(form.getGitHub());
			userDB.get().setCity(form.getCity());
			userDB.get().setState(form.getState());
			userDB.get().setGender(form.getGender());
			return Optional.of(repository.save(userDB.get()));
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);
	}
    
    private String encryptedPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String passwordEncoder = encoder.encode(password);
		return passwordEncoder;
	}
}