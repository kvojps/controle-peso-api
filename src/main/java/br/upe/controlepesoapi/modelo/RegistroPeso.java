package br.upe.controlepesoapi.modelo;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroPeso {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotEmpty(message = "Informe o peso")
	private int peso;
	
//	@NotEmpty(message = "Informe a data")
	private LocalDateTime data;
	
    @NotNull(message = "Informe o usuario associado a senha")
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
