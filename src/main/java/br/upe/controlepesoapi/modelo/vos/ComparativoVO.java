package br.upe.controlepesoapi.modelo.vos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComparativoVO {
  private double medidaAtual;
  private double medida7DiasAtras;
  private double medida30DiasAtras;
  private double medida1AnoAtras;
}
