package org.generation.blogpessoal.controller;

	import java.util.List;
	import java.util.Optional;

	import javax.validation.Valid;

import org.generation.blogpessoal.model.UsuarioLogin;
import org.generation.blogpessoal.model.UsuarioModel;
import org.generation.blogpessoal.repository.UsuarioRepository;
import org.generation.blogpessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.CrossOrigin;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;

	@RestController
	@RequestMapping("/usuarios")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public class UsuarioController {

	    @Autowired
	    private UsuarioService service;

	    @Autowired
	    private UsuarioRepository repository;
	    
	    @GetMapping("/all")
	    public ResponseEntity <List<UsuarioModel>> getAll(){     
	        return ResponseEntity.ok(repository.findAll());  
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<UsuarioModel> getById(@PathVariable Long id) {
	        return repository.findById(id)
	            .map(resposta -> ResponseEntity.ok(resposta))
	            .orElse(ResponseEntity.notFound().build());
	    }
	    
	    @PostMapping("/logar")
	    public ResponseEntity<UsuarioLogin> login(@RequestBody Optional<UsuarioLogin> usuarioLogin) {
	        return service.autenticarUsuario(usuarioLogin)
	            .map(resposta -> ResponseEntity.ok(resposta))
	            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	    }

	    @PostMapping("/cadastrar")
	    public ResponseEntity<UsuarioModel> postUsuario(@Valid @RequestBody UsuarioModel usuario) {

	        return service.cadastrarUsuario(usuario)
	            .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
	            .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

	    }

	    @PutMapping("/atualizar")
	    public ResponseEntity<UsuarioModel> putUsuario(@Valid @RequestBody UsuarioModel usuario) {
	        return service.atualizarUsuario(usuario)
	            .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
	            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	    }
	
}
