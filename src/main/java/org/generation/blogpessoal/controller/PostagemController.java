package org.generation.blogpessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.generation.blogpessoal.model.PostagemModel;
import org.generation.blogpessoal.repository.PostagemRepository;
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


//indica para o spring que a classe é do tipo controladora rest, que a classe irá ser acionada a partir da URL(começa a atender endpoints), 
//e RequestBody objeto que contém os dados que serão criados ou atualizados
@RestController 
@RequestMapping("/postagens") //mapear o caminho para acessar o ponto de acesso pela API Postman
@CrossOrigin(origins = "*", allowedHeaders = "*") //para o frontend consumir esse API de qualquer linguagem que seja o front;
//e para liberar o backend para uma aplicação externa em outra porta 

public class PostagemController {

	
	@Autowired //como não conseguimos instanciar uma interface, precisamos utilizar para encarrregar o spring de instanciar tudo o que precisa ser carregado
	private PostagemRepository repository;
	
	@GetMapping //sempre que vir uma requisição externa, de alguem que consuma essa API atraves de
	//postagens e for um metodo GET vai disparar esse metodo
	public ResponseEntity<List<PostagemModel>> getAll (){
		return ResponseEntity.ok(repository.findAll());
	//ResponseEntity<> é uma interface que monta um template no protocolo HTTP passando na body lista de produtos em formato Json
	//findAll é implementado pela JPA e usado para carregar todos os dados na tabela 
	}
	
	@GetMapping("/{id}") //findbyid retorna apenas um item da nossa tabela
	public ResponseEntity<PostagemModel> getById(@PathVariable Long id) { //pathvariable é uma forma de passar valores para a nossa API
		return repository.findById(id) 
			.map(resposta -> ResponseEntity.ok(resposta)) //metodo map() com empressao lambda expression para resposta positiva e para informar que caso tenha algum nulo o response sera um 404 not found 
			.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<PostagemModel>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<PostagemModel> postPostagem (@Valid @RequestBody PostagemModel postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}
	
	@PutMapping
	public ResponseEntity<PostagemModel> putPostagem (@RequestBody PostagemModel postagem){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
	}
	
	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable Long id) {
		repository.deleteById(id);
	}
}