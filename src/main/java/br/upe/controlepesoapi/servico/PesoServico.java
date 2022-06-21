package br.upe.controlepesoapi.servico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.upe.controlepesoapi.excecao.ControlePesoException;
import br.upe.controlepesoapi.modelo.ImcEnum;
import br.upe.controlepesoapi.modelo.entidades.RegistroPeso;
import br.upe.controlepesoapi.modelo.entidades.Usuario;
import br.upe.controlepesoapi.modelo.vos.ComparativoVO;
import br.upe.controlepesoapi.modelo.vos.DashboardVO;
import br.upe.controlepesoapi.modelo.vos.EvolucaoVO;
import br.upe.controlepesoapi.modelo.vos.HistoricoVO;
import br.upe.controlepesoapi.modelo.vos.ImcVO;
import br.upe.controlepesoapi.modelo.vos.MonitoramentoVO;
import br.upe.controlepesoapi.repositorio.IRegistroPesoRepositorio;
import br.upe.controlepesoapi.repositorio.IUsuarioRepositorio;

@Service
public class PesoServico {
  @Autowired
  private IRegistroPesoRepositorio pesoRepositorio;

  @Autowired
  private IUsuarioRepositorio usuarioRepositorio;

  public DashboardVO gerarDashboardVO(String email) {

    Optional<Usuario> usuario = usuarioRepositorio.findByEmailIgnoreCase(email);
    validarUsuario(usuario);
    Optional<RegistroPeso> peso = pesoRepositorio.findFirstByUsuarioEmailOrderByDataDesc(email);
    validarPeso(peso);

    return DashboardVO.builder().monitoramento(gerarMonitoramentoVO(usuario, peso))
        .evolucao(gerarEvolucaoVO(usuario, peso)).imc(gerarImcVO(usuario, peso))
        .comparativo(gerarComparativoVO(usuario)).historico(gerarHistoricoVO(usuario)).build();

  }

  private MonitoramentoVO gerarMonitoramentoVO(Optional<Usuario> usuario,
      Optional<RegistroPeso> peso) {
    double pesoInicial = usuario.get().getRegistrosPeso().get(0).getPeso();
    LocalDate dataPesoInicial = usuario.get().getRegistrosPeso().get(0).getData();

    double pesoAtual = peso.get().getPeso();
    int ultimoIndex = usuario.get().getRegistrosPeso().size() - 1;
    LocalDate dataPesoAtual = usuario.get().getRegistrosPeso().get(ultimoIndex).getData();

    double pesoDesejado = usuario.get().getPesoDesejado();
    LocalDate dataPesoDesejado = usuario.get().getDataPesoDesejado();

    return MonitoramentoVO.builder().pesoInicial(pesoInicial).dataPesoInicial(dataPesoInicial)
        .pesoAtual(pesoAtual).dataPesoAtual(dataPesoAtual).pesoObjetivo(pesoDesejado)
        .dataPesoObjetivo(dataPesoDesejado).build();
  }

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
    double altura = usuario.get().getAltura() / 100.0;
    double peso = registroPeso.get().getPeso();
    double imc = (peso / (altura * altura));
    double imcAproximado = BigDecimal.valueOf(imc).setScale(2, RoundingMode.HALF_UP).doubleValue();

    ImcEnum grau = ImcEnum.ABAIXO_DO_PESO;

    if (imc > 18.5 && imc < 24.9) {
      grau = ImcEnum.PESO_IDEAL;
    } else if (imc > 25) {
      grau = ImcEnum.ACIMA_DO_PESO;
    }

    return ImcVO.builder().imc(imcAproximado).classificacao(grau).build();
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

  private HistoricoVO gerarHistoricoVO(Optional<Usuario> usuario) {
    List<RegistroPeso> pesos =
        pesoRepositorio.findByUsuarioEmailOrderByDataAsc(usuario.get().getEmail());

    HistoricoVO historico = HistoricoVO.builder().pesos(pesos).build();

    return historico;
  }

  private void validarUsuario(Optional<Usuario> usuario) {
    if (usuario.isEmpty()) {
      throw new ControlePesoException("Usuário não cadastrado");
    }
  }

  private void validarPeso(Optional<RegistroPeso> peso) {
    if (peso.isEmpty()) {
      throw new ControlePesoException("Registro de peso não encontrado");
    }
  }

}
