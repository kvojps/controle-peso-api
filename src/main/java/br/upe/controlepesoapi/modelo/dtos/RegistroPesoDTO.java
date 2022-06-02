package br.upe.controlepesoapi.modelo.dtos;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.upe.controlepesoapi.modelo.entidades.RegistroPeso;
import br.upe.controlepesoapi.modelo.entidades.Usuario;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegistroPesoDTO {
	private Long id;

	@NotEmpty(message = "Informe o peso")
	private int peso;

	@NotEmpty(message = "Informe a data")
	private LocalDateTime data;

	@NotNull(message = "Informe o identificador do usuário")
	private Long idUsuario;
}
