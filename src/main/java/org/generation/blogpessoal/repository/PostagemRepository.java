package org.generation.blogpessoal.repository;

import java.util.List;

import org.generation.blogpessoal.model.PostagemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostagemRepository extends JpaRepository <PostagemModel, Long>{ //importada a classe postagem e long por ser o tipo da nossa ID
	//extendemos da interface chamada JPARepository, dentro dela temos os metodos para consultar, deletar, atualizar e incluir dados 
	
	public List<PostagemModel> findAllByTituloContainingIgnoreCase(@Param("titulo")String titulo);
	//vai buscar todos pelo titulo que é o nome do atributo da minha entidade postagem sem considerar letra maiscula ou minuscula
	//o ignore case ignora se é maiusculo do minusculo e padroniza todos em minusculo para o programa localizar;
	
	/*SELECT * FROM tb_postagens WHERE titulo = "" */
	/*SELECT * FROM tb_postagens WHERE titulo LIKE = "%titulo%" */
}
