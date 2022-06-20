package br.upe.controlepesoapi.servico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.upe.controlepesoapi.modelo.entidades.RegistroPeso;
import br.upe.controlepesoapi.modelo.entidades.Usuario;
import br.upe.controlepesoapi.modelo.vos.EvolucaoVO;
import br.upe.controlepesoapi.modelo.vos.HistoricoVO;
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

  private HistoricoVO gerarHistoricoVO(String email) {
    List<RegistroPeso> pesos = pesoRepositorio.findByUsuarioEmailOrderByDataAsc(email);

    HistoricoVO historico = HistoricoVO.builder().pesos(pesos).build();

    return historico;
  }
}
