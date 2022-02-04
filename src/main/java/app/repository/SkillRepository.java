package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Skill;
import app.util.Nivel;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>{

	public List<Skill> findAllBySkillContainingIgnoreCase(String skill);
	
	public List<Skill> findAllByNivel(Nivel nivel);
}