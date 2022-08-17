package org.generation.blogpessoal.repository;


import java.util.Optional;
import java.util.List;

import org.generation.blogpessoal.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long>{

		public Optional<UsuarioModel> findByUsuario(String usuario);
		
		public List <UsuarioModel> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);	
}
