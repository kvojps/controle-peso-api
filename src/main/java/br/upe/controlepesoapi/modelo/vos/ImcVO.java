package br.upe.controlepesoapi.modelo.vos;

import br.upe.controlepesoapi.modelo.ImcEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImcVO {
  private double imc;
  private ImcEnum classificacao;
}
