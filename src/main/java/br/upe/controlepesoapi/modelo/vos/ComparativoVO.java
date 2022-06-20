package br.upe.controlepesoapi.modelo.vos;

import lombok.Data;

@Data
public class ComparativoVO {
  private double medidaAtual;
  private double medida7DiasAtras;
  private double medida30DiasAtras;
  private double medida1AnoAtras;
}
