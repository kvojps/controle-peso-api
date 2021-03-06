package br.upe.controlepesoapi.servico;

import java.util.List;
import java.util.Optional;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.upe.controlepesoapi.excecao.ControlePesoException;
import br.upe.controlepesoapi.excecao.NaoEncontradoException;
import br.upe.controlepesoapi.modelo.entidades.RegistroPeso;
import br.upe.controlepesoapi.modelo.entidades.Usuario;
import br.upe.controlepesoapi.repositorio.IRegistroPesoRepositorio;
import br.upe.controlepesoapi.repositorio.IUsuarioRepositorio;

@Service
public class UsuarioServico implements IUsuarioServico {

  @Autowired
  private IUsuarioRepositorio usuarioRepositorio;
  @Autowired
  private IRegistroPesoRepositorio registroPesoRepositorio;

  @Override
  public List<Usuario> listarUsuarios() {
    return (List<Usuario>) usuarioRepositorio.findAll();
  }

  @Override
  public Usuario incluirUsuario(Usuario usuario) {
    validarInclusaoUsuario(usuario);
    return usuarioRepositorio.save(usuario);
  }

  @Override
  public Usuario alterarUsuario(Usuario usuario) {
    validarAlteracaoUsuario(usuario);
    return usuarioRepositorio.save(usuario);
  }

  @Override
  public void excluirUsuario(Long id) {
    validarExclusaoUsuario(id);
    usuarioRepositorio.deleteById(id);
  }

  @Override
  public void adicionarPesoAoUsuario(String email, int peso) {
    Optional<Usuario> usuario = usuarioRepositorio.findByEmailIgnoreCase(email);
    RegistroPeso pesoNovo = new RegistroPeso();
    pesoNovo.setUsuario(usuario.get());
    pesoNovo.setData(LocalDate.now());
    pesoNovo.setPeso(peso);
    registroPesoRepositorio.save(pesoNovo);
    usuario.get().getRegistrosPeso().add(pesoNovo);
  }

  private void validarInclusaoUsuario(Usuario usuario) {
    if (usuario == null) {
      throw new ControlePesoException("Dados nulos");
    }

    if (usuarioRepositorio.findByEmailIgnoreCase(usuario.getEmail()).isPresent()) {
      throw new ControlePesoException(
          "Ocorreu um erro ao incluir o usu??rio: j?? existe usu??rio cadastrado com o email"
              + usuario.getEmail());
    }

    if (usuario.getAltura() < 100) {
      throw new ControlePesoException("Por favor preencha corretamente o campo altura.");
    }

    validarData(usuario.getRegistrosPeso().get(0).getData(), usuario.getDataPesoDesejado());
  }

  private void validarAlteracaoUsuario(Usuario usuario) {
    if (usuario == null) {
      throw new ControlePesoException("Dados nulos");
    }

    if (usuarioRepositorio.findByEmailIgnoreCase(usuario.getEmail()).isEmpty()) {
      throw new ControlePesoException(
          "Ocorreu um erro ao alterar o usu??rio: N??o existe usu??rio cadastrado com o email"
              + usuario.getEmail());
    }

    if (usuario.getAltura() < 100) {
      throw new ControlePesoException("Por favor preencha corretamente o campo altura.");
    }
  }

  private void validarExclusaoUsuario(Long id) {
    if (id == null) {
      throw new ControlePesoException(
          "Ocorreu um erro ao excluir o usu??rio: Informe o identificador");
    }

    if (!usuarioRepositorio.existsById(id)) {
      throw new NaoEncontradoException(
          "Ocorreu um erro ao excluir usu??rio: usu??rio n??o encontrado");
    }
  }

  private void validarData(LocalDate dataInicio, LocalDate dataObjetivo) {
    Days dias = Days.daysBetween(dataInicio, dataObjetivo);

    if (dias.getDays() < 7) {
      throw new ControlePesoException(
          "A data entreo o peso inicial e o objetivo deve ser de no m??nimo uma semana.");
    }
  }

}
