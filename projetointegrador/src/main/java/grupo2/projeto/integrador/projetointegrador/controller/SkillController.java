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

import grupo2.projeto.integrador.projetointegrador.model.Skill;
import grupo2.projeto.integrador.projetointegrador.repository.SkillRepository;

@RestController
@RequestMapping("/skill")
@CrossOrigin("*")

public class SkillController {
	@Autowired
	private SkillRepository repository;

	// GET
	@GetMapping("/All")
	public ResponseEntity<List<Skill>> getAll() {
		List<Skill> listSkill = repository.findAll();
		if (listSkill.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return ResponseEntity.ok(listSkill);

		}

	}

}
