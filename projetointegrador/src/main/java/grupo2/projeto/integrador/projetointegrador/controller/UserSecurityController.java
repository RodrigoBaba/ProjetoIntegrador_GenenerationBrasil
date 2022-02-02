package grupo2.projeto.integrador.projetointegrador.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import grupo2.projeto.integrador.projetointegrador.model.UserSecurityLogin;
import grupo2.projeto.integrador.projetointegrador.model.Usuario;
import grupo2.projeto.integrador.projetointegrador.service.UserService;

@RestController
@RequestMapping("/userSecurity")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserSecurityController {

	@Autowired
	private UserService userService;

	@PostMapping("/logar")
	public ResponseEntity<UserSecurityLogin> Autentication(@RequestBody Optional<UserSecurityLogin> user) {
		return userService.Logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(401).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> Post(@RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(userService.CadastrarUsuario(usuario));
	}

}
