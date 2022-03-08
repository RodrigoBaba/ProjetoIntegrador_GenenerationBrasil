package app.repository;

import app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public List<User> findAllByCityContainingIgnoreCase(String cityUser);

	public Optional<User> findByEmail(String email);
	
	public List<User> findAllByGenderContainingIgnoreCase(String gender);

}