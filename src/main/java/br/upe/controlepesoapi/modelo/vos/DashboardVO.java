package br.upe.controlepesoapi.modelo.vos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardVO {
  private MonitoramentoVO monitoramento;
  private EvolucaoVO evolucao;
  private ImcVO imc;
  private ComparativoVO comparativo;
  private HistoricoVO historico;
}
