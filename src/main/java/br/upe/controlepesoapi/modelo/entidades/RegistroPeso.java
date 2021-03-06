package br.upe.controlepesoapi.modelo.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.joda.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroPeso {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotEmpty(message = "Informe o peso")
  private int peso;

  @NotEmpty(message = "Informe a data")
  private LocalDate data;

  @NotNull(message = "Informe o usuario associado a senha")
  @ManyToOne
  @JoinColumn(name = "id_usuario")
  private Usuario usuario;
}
