package grupo2.projeto.integrador.projetointegrador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import grupo2.projeto.integrador.projetointegrador.model.Usuario;
import grupo2.projeto.integrador.projetointegrador.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> getAll () { 
		List<Usuario> listUsuario = repository.findAll();
		if (listUsuario.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return ResponseEntity.ok(listUsuario);	
		}
	}
}

