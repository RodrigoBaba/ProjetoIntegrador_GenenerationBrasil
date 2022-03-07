package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

	public List<Card> findAllByOccupationContainingIgnoreCase(String occupation);
	
	public List<Card> findAllByFormationContainingIgnoreCase(String formation);
}