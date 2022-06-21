package br.upe.controlepesoapi.servico;

import br.upe.controlepesoapi.modelo.vos.DashboardVO;

public interface IPesoServico {

  DashboardVO gerarDashboardVO(String email);

}
