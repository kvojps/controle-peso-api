package br.upe.controlepesoapi.modelo.vos;

import java.util.List;
import br.upe.controlepesoapi.modelo.entidades.RegistroPeso;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoricoVO {
  private List<RegistroPeso> pesos;
}
