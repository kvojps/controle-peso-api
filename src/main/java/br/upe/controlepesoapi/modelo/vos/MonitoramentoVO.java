package br.upe.controlepesoapi.modelo.vos;

import org.joda.time.DateTime;
import lombok.Data;

@Data
public class MonitoramentoVO {
  private double pesoInicial;
  private DateTime dataPesoInicial;

  private double pesoAtual;
  private DateTime dataPesoAtual;

  private double pesoObjetivo;
  private DateTime dataPesoObjetivo;
}
