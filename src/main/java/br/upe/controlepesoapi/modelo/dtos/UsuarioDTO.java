package br.upe.controlepesoapi.modelo.dtos;

import java.beans.Transient;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.upe.controlepesoapi.modelo.entidades.Usuario;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioDTO {
	private Long id;

	@NotEmpty(message = "Informe o nome do usuário")
	private String nome;

	@NotEmpty(message = "Informe o e-mail do usuário")
	private String email;

	@NotEmpty(message = "Informe a altura do usuário")
	private int altura;

	private String sexo;

	private int pesoDesejado;

	private List<RegistroPesoDTO> registrosPeso;

	@Transient
	@JsonIgnore
	public Usuario getUsuario() {
		return Usuario.builder().nome(this.nome).email(this.email).altura(this.altura).sexo(this.sexo)
				.pesoDesejado(this.pesoDesejado).build();
	}

}
