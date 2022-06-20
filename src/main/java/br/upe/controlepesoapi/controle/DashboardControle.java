package br.upe.controlepesoapi.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.upe.controlepesoapi.modelo.vos.DashboardVO;
import br.upe.controlepesoapi.servico.PesoServico;

@RequestMapping("/api")
@RestController
public class DashboardControle {

  @Autowired
  private PesoServico pesoServico;

  @GetMapping("/dashboard")
  public DashboardVO gerarDashboard(@RequestParam String email) {
    return pesoServico.gerarDashboardVO(email);
  }

}
