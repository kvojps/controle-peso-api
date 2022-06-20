package br.upe.controlepesoapi.modelo.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.joda.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegistroPesoDTO {
  private Long id;

  @NotEmpty(message = "Informe o peso")
  private int peso;

  @NotEmpty(message = "Informe a data")
  private LocalDate data;

  @NotNull(message = "Informe o identificador do usuário")
  private Long idUsuario;
}
