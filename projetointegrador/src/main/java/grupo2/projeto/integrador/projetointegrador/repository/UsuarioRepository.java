package grupo2.projeto.integrador.projetointegrador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grupo2.projeto.integrador.projetointegrador.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public List<Usuario> findByCityContainingIgnoreCase(String cityUser);

	public List<Usuario> findAllCityContainingIgnoreCase(String cityUser);
}
