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

import app.model.Card;
import app.repository.CardRepository;
import app.util.Option;

@RestController
@RequestMapping("/card")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CardController {

    @Autowired
    private CardRepository repository;

    @GetMapping
    public ResponseEntity<List<Card>> getAll() {
        List<Card> ListCard = repository.findAll();

        if (ListCard.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        } else {

            return ResponseEntity.ok(ListCard);
        }
    }

    @GetMapping("/select/{id}")
    public ResponseEntity<Card> findById(@PathVariable("id") Long idvariavel) {
        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(idvariavel).get());
    }

    @GetMapping("/occupation/{occupation}")
    public ResponseEntity<List<Card>> findAllByOccupation(@PathVariable("occupation") String occupation) {
        List<Card> list = repository.findAllByOccupationContainingIgnoreCase(occupation);

        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deu Ruim!");

        } else {
            return ResponseEntity.ok(list);

        }
    }
    
    @GetMapping("/formation/{formation}")
    public ResponseEntity<List<Card>> findAllByFormation(@PathVariable("formation") String formation) {
        List<Card> list = repository.findAllByFormationContainingIgnoreCase(formation);

        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deu Ruim!");

        } else {
            return ResponseEntity.ok(list);

        }
    }
    
    @GetMapping("/option/{optional}")
	public ResponseEntity<List<Card>> findAllByOption(@PathVariable("optional") Option optional) {
		List<Card> list = repository.findAllByOptional(optional);
		return ResponseEntity.ok(list);
	}

    @PostMapping("/insert")
    public ResponseEntity<Card> insert(@Valid @RequestBody Card postCard) {
        return ResponseEntity.status(201).body(repository.save(postCard));

    }
    
    @PutMapping("/update")
	public ResponseEntity<Card> putCard(@Valid @RequestBody Card altCard) {
		return repository.findById(altCard.getId())
				.map(resp -> ResponseEntity.status(200).body(repository.save(altCard))).orElseGet(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não encontrado");
				});
	}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Optional<Card> optional = repository.findById(id);

        if (optional.isPresent()) {

            repository.deleteById(id);
            return ResponseEntity.status(200).build();

        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id não encontrado!");

        }
    }

}