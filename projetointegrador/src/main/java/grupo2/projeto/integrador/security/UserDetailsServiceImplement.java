package grupo2.projeto.integrador.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import grupo2.projeto.integrador.projetointegrador.model.Usuario;
import grupo2.projeto.integrador.projetointegrador.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImplement implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetailsImp loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> user = repository.findByEmail(email);
		user.orElseThrow(() -> new UsernameNotFoundException(email + "Not_Found"));

		return user.map(UserDetailsImp::new).get();
	}

}

