package app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public List<User> findAllByCityContainingIgnoreCase(String cityUser);

	public Optional<User> findByEmail(String email);
}