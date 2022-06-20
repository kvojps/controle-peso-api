package br.upe.controlepesoapi.modelo.vos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EvolucaoVO {
  private double progresso;
  private double tempo;
}
