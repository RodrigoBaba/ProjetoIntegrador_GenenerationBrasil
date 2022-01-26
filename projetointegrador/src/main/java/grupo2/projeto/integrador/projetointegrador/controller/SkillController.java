package grupo2.projeto.integrador.projetointegrador.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	//DELETE
		@DeleteMapping("/delete/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id){
			
			Optional<Skill> optional = repository.findById(id);
			
				if (optional.isPresent()) {
					
					repository.deleteById(id);
					return ResponseEntity.status(200).build();
					
				} else {
					
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id não encontrado!");
					
				}
				
		}

	@GetMapping("/select/{id}")

	public ResponseEntity<Skill> findById(@PathVariable("id") Long idvariavel) {
		return ResponseEntity.status(HttpStatus.OK).body(repository.findById(idvariavel).get());
		 }

	@PutMapping("/alterar/{id}")
	public ResponseEntity<Skill> putSkill(@RequestBody Skill altSkill) {
		return repository.findById(altSkill.getId()).map(resp -> ResponseEntity.status(200)
				.body(repository.save(altSkill)))
				.orElseGet(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não encontrado");
				});
	}

}
