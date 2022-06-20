package br.upe.controlepesoapi.modelo.vos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MonitoramentoVO {
  private ImcVO imc;
  private EvolucaoVO evolucao;
  private HistoricoVO historico;
  private ComparativoVO comparativo;
}
