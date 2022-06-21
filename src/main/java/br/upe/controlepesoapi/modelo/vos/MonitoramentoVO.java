package br.upe.controlepesoapi.modelo.vos;

import org.joda.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MonitoramentoVO {
  private double pesoInicial;
  private LocalDate dataPesoInicial;

  private double pesoAtual;
  private LocalDate dataPesoAtual;

  private double pesoObjetivo;
  private LocalDate dataPesoObjetivo;
}
