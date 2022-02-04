package app.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.model.UserSecurityLogin;
import app.model.Usuario;
import app.repository.UsuarioRepository;
import app.service.UserService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired UserService service;

	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> getAll() {
		List<Usuario> listUsuario = repository.findAll();
		if (listUsuario.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return ResponseEntity.ok(listUsuario);
		}
	}
	
	@GetMapping("/id/{id_usuario}")
	public ResponseEntity<Usuario> getById(@PathVariable(value = "id_usuario") Long id){
		return repository.findById(id).map(resp -> ResponseEntity.status(200).body(resp))
				.orElseGet(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não encontrado!");
				});
	}
		
	@GetMapping("/allcity/{cityUser}")
	public ResponseEntity<List<Usuario>> getAllCity(@PathVariable(value = "cityUser") String cityUser) {
		List<Usuario> listCity = repository.findAllByCityContainingIgnoreCase(cityUser);

		if (listCity.isEmpty()) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

		} else {

			return ResponseEntity.ok(listCity);

		}

	}
	
	@PostMapping("/logar")
	public ResponseEntity<UserSecurityLogin> autentication(@RequestBody Optional<UserSecurityLogin> user) {
		return service.login(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(401).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> post(@RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(service.saveUser(usuario));
	}

	@PutMapping("/update")
	public ResponseEntity<Usuario> updateUsuario(@Valid @RequestBody Usuario usuario) {
		return repository.findById(usuario.getId())
				.map(resp -> ResponseEntity.status(200).body(repository.save(usuario)))
				.orElseGet(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não encontrado!");
				});
	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping("/delete/{id_usuario}")
	public ResponseEntity deleteUsuario(@PathVariable(value = "id_usuario") Long id) {
		Optional<Usuario> optional = repository.findById(id);

		if (optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.status(200).build();
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não encontrado!");
		}
	}

}