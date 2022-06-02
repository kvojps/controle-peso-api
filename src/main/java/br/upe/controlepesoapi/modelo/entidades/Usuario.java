package br.upe.controlepesoapi.modelo.entidades;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotEmpty(message = "Informe o nome do usuário")
	private String nome;

	@NotEmpty(message = "Informe o e-mail do usuário")
	@Email(message = "Informe o e-mail em um formato válido")
	private String email;

	@NotEmpty(message = "Informe a altura do usuário")
	private int altura;

	private String sexo;
	private int pesoDesejado;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<RegistroPeso> registrosPeso;

}
