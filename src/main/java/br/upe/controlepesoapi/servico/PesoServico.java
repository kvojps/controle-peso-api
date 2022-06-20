package br.upe.controlepesoapi.servico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.upe.controlepesoapi.modelo.entidades.RegistroPeso;
import br.upe.controlepesoapi.modelo.entidades.Usuario;
import br.upe.controlepesoapi.modelo.vos.ComparativoVO;
import br.upe.controlepesoapi.modelo.vos.EvolucaoVO;
import br.upe.controlepesoapi.modelo.vos.HistoricoVO;
import br.upe.controlepesoapi.modelo.vos.ImcVO;
import br.upe.controlepesoapi.repositorio.IRegistroPesoRepositorio;
import br.upe.controlepesoapi.repositorio.IUsuarioRepositorio;

@Service
public class PesoServico {
  @Autowired
  private IRegistroPesoRepositorio pesoRepositorio;

  @Autowired
  private IUsuarioRepositorio usuarioRepositorio;

  private EvolucaoVO gerarEvolucaoVO(Optional<Usuario> usuario, Optional<RegistroPeso> peso) {
    double pesoDesejado = usuario.get().getPesoDesejado();
    double pesoInicial = usuario.get().getRegistrosPeso().get(0).getPeso();
    double pesoAtual = peso.get().getPeso();

    double progresso = (1 - (pesoAtual - pesoDesejado) / (pesoInicial - pesoDesejado)) * 100;
    double progressoAprox =
        BigDecimal.valueOf(progresso).setScale(1, RoundingMode.HALF_EVEN).doubleValue();

    double diasPassados =
        Days.daysBetween(usuario.get().getRegistrosPeso().get(0).getData(), peso.get().getData())
            .getDays();
    double diasPrevistos = Days.daysBetween(usuario.get().getRegistrosPeso().get(0).getData(),
        usuario.get().getDataPesoDesejado()).getDays();

    double tempo = (diasPassados / diasPrevistos) * 100;
    double tempoAprox = BigDecimal.valueOf(tempo).setScale(1, RoundingMode.HALF_EVEN).doubleValue();

    return EvolucaoVO.builder().progresso(progressoAprox).tempo(tempoAprox).build();
  }

  private ImcVO gerarImcVO(Optional<Usuario> usuario, Optional<RegistroPeso> registroPeso) {

  }

  private ComparativoVO gerarComparativoVO(Optional<Usuario> usuario) {
    List<RegistroPeso> pesos =
        pesoRepositorio.findByUsuarioEmailOrderByDataAsc(usuario.get().getEmail());

    ComparativoVO comparativo = new ComparativoVO();

    LocalDate dataPesoInicial = usuario.get().getRegistrosPeso().get(0).getData();

    pesos.forEach(peso -> {
      if (Days.daysBetween(dataPesoInicial, peso.getData()).getDays() == 7) {
        comparativo.setMedida7DiasAtras(peso.getPeso());
      } else if (Days.daysBetween(dataPesoInicial, peso.getData()).getDays() == 30) {
        comparativo.setMedida30DiasAtras(peso.getPeso());
      } else if (Days.daysBetween(dataPesoInicial, peso.getData()).getDays() == 365) {
        comparativo.setMedida1AnoAtras(peso.getPeso());
      }
    });

    comparativo.setMedidaAtual(pesos.get(pesos.size() - 1).getPeso());
    return comparativo;
  }

  private HistoricoVO gerarHistoricoVO(String email) {
    List<RegistroPeso> pesos = pesoRepositorio.findByUsuarioEmailOrderByDataAsc(email);

    HistoricoVO historico = HistoricoVO.builder().pesos(pesos).build();

    return historico;
  }


}
