package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Card;
import app.util.Option;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

	public List<Card> findAllByOccupationContainingIgnoreCase(String occupation);
	
	public List<Card> findAllByFormationContainingIgnoreCase(String formation);
	
	public List<Card> findAllByOptional(Option option);

}