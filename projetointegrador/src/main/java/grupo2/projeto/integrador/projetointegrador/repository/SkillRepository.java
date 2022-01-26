package grupo2.projeto.integrador.projetointegrador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grupo2.projeto.integrador.projetointegrador.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>{

	public List<Skill> findAllBySkillContainingIgnoreCase(String skill);
	
}
