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
import app.model.User;
import app.repository.UserRepository;
import app.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserRepository repository;

	@Autowired
	UserService service;

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAll() {
		List<User> listUsuario = repository.findAll();
		if (listUsuario.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return ResponseEntity.ok(listUsuario);
		}
	}

	@GetMapping("/id/{id_user}")
	public ResponseEntity<User> getById(@PathVariable(value = "id_user") Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.status(200).body(resp))
				.orElseGet(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não encontrado!");
				});
	}

	@GetMapping("/allcity/{cityUser}")
	public ResponseEntity<List<User>> getAllCity(@PathVariable(value = "cityUser") String cityUser) {
		List<User> listCity = repository.findAllByCityContainingIgnoreCase(cityUser);

		if (listCity.isEmpty()) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

		} else {

			return ResponseEntity.ok(listCity);
		}
	}
	
	@GetMapping("/gender/{gender}")
	public ResponseEntity<List<User>> getAllGender(@PathVariable(value = "gender") String gender) {
		List<User> list = repository.findAllByGenderContainingIgnoreCase(gender);

		if (list.isEmpty()) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

		} else {

			return ResponseEntity.ok(list);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<UserSecurityLogin> autentication(@RequestBody Optional<UserSecurityLogin> user) {
		return service.login(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(401).build());
	}

	@PostMapping("/register")
	public ResponseEntity<User> post(@Valid @RequestBody User user) {
		return service.saveUser(user)
				.map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@PutMapping("/update")
	public ResponseEntity<User> updateUsuario(@Valid @RequestBody User usuario) {
		return service.updateUser(usuario)
				.map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PutMapping("/updateBio")
	public ResponseEntity<User> updateBio(@Valid @RequestBody User usuario) {
		return service.updateBio(usuario)
				.map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping("/delete/{id_user}")
	public ResponseEntity deleteUsuario(@PathVariable(value = "id_user") Long id) {
		Optional<User> optional = repository.findById(id);

		if (optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.status(200).build();
		} else {
			throw new ResponseStatusException(HttpStatus.CREATED, "Id não encontrado!");
		}
	}

}