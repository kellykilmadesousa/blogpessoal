package org.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.generation.blogpessoal.model.UsuarioModel;
import org.generation.blogpessoal.repository.UsuarioRepository;
import org.generation.blogpessoal.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService service;
	
	@Autowired
	private UsuarioRepository repository;

	@BeforeAll
	void start() {

		repository.deleteAll();

		service.cadastrarUsuario(new UsuarioModel(0L, "rootroot", "root@root.com", "rootroot", " "));
	}

	@Test
	@Order(1)
	@DisplayName("Cadastrar Um Usuário")
	public void deveCriarUmUsuario() {

		HttpEntity<UsuarioModel> corpoRequisicao = new HttpEntity<UsuarioModel>(new UsuarioModel(0L, 
			"Paulo Antunes", "paulo_antunes@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));


		ResponseEntity<UsuarioModel> corpoResposta = testRestTemplate
			.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, UsuarioModel.class);
		
		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());

		assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
		
		assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
	}

	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {

		service.cadastrarUsuario(new UsuarioModel(0L, 
			"Maria da Silva", "maria_silva@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg"));
		
		HttpEntity<UsuarioModel> corpoRequisicao = new HttpEntity<UsuarioModel>(new UsuarioModel(0L, 
			"Maria da Silva", "maria_silva@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg"));

		ResponseEntity<UsuarioModel> corpoResposta = testRestTemplate
			.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, UsuarioModel.class);

		assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
	}

	@Test
	@Order(3)
	@DisplayName("Atualizar um Usuário")
	public void deveAtualizarUmUsuario() {

		Optional<UsuarioModel> usuarioCadastrado = service.cadastrarUsuario(new UsuarioModel(0L, 
			"Juliana Andrews", "juliana_andrews@email.com.br", "juliana123", "https://i.imgur.com/yDRVeK7.jpg"));
		
		UsuarioModel usuarioUpdate = new UsuarioModel(usuarioCadastrado.get().getId(), 
			"Juliana Andrews Ramos", "juliana_ramos@email.com.br", "juliana123" , "https://i.imgur.com/yDRVeK7.jpg");
		
		HttpEntity<UsuarioModel> corpoRequisicao = new HttpEntity<UsuarioModel>(usuarioUpdate);

		ResponseEntity<UsuarioModel> corpoResposta = testRestTemplate
			.withBasicAuth("root@root.com", "rootroot")
			.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, UsuarioModel.class);

		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());

		assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());

		assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
	}

	@Test
	@Order(4)
	@DisplayName("Listar todos os Usuários")
	public void deveMostrarTodosUsuarios() {

		service.cadastrarUsuario(new UsuarioModel(0L, 
			"Sabrina Sanches", "sabrina_sanches@email.com.br", "sabrina123", "https://i.imgur.com/5M2p5Wb.jpg"));
		
		service.cadastrarUsuario(new UsuarioModel(0L, 
			"Ricardo Marques", "ricardo_marques@email.com.br", "ricardo123", "https://i.imgur.com/Sk5SjWE.jpg"));

		ResponseEntity<String> resposta = testRestTemplate
			.withBasicAuth("root@root.com", "rootroot")
			.exchange("/usuarios/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());

	}
}
