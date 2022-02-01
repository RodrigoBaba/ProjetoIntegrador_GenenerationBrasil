package grupo2.projeto.integrador.projetointegrador.controller;

import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.model.UsuarioLogin;
import org.generation.blogPessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userSecurity")
@CrossOrigin(origins = "*", allowedHeaders = "*" )
public class UserSecurityController {

	@Autowired
	private UsuarioService usuarioService;
	
@PostMapping("/logar")
public ResponseEntity<UsuarioLogin> Autentication(@RequestBody Optional<UsuarioLogin> user){
	return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp))
			.orElse(ResponseEntity.status(401).build());
}

@PostMapping("/cadastrar")	public ResponseEntity<Usuario> Post(@RequestBody Usuario usuario){
	return ResponseEntity.status(HttpStatus.CREATED)
			.body(usuarioService.CadastrarUsuario(usuario));
}
	
}
