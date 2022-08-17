package org.generation.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity //essa anotação indica que a classe é uma entidade, será utilizada para gerar uma tabela no BD.
@Table (name = "tb_postagens") //indica o nome da tabela no BD. Caso ela não seja declarada, o banco criará a tabela com o mesmo nome da classe.
public class PostagemModel {

	@Id //indica id como uma primary key no BD
	@GeneratedValue(strategy = GenerationType.IDENTITY) //indica o esquema autoincrement para a chave primaria que será criada pelo BD
	private Long id; 
	//Long com L maiúsculo é uma classe wrapper, permite que a gente use aqueles getters e setter do java 
	//já o long com l minúsculo é uma classe primitiva, também permite os getters e setters mas pode dar conflito de tipagem

	//serve para validar se valor inserido não está vazio, nulo e se tem espaço (geralmente para textos menores)
	@NotBlank(message = "O atributo título é Obrigatório e não pode utilizar espaços em branco!") 
	@Size(min = 5, max = 100, message = "O atributo título deve conter no mínimo 05 e no máximo 100 caracteres") //quantidade de caracter 
	private String titulo;
	
	@NotNull(message = "O atributo texto é Obrigatório!") //serve para valida se está nulo e se tem espaço, geralmente textos maiores 
	@Size(min = 10, max = 1000, message = "O atributo texto deve conter no mínimo 10 e no máximo 1000 caracteres")
	private String texto;
	
	@UpdateTimestamp  //pega localdatahora do seu computador automaticamente
	private LocalDateTime data;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private TemaModel tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private UsuarioModel usuario;
	
	public Long getId() {
		return id;
	} 

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
	public TemaModel getTema() {
		return tema;
	}

	public void setTema(TemaModel tema) {
		this.tema = tema;
	}

	public UsuarioModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}
	
	
}
