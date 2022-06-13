package br.upe.controlepesoapi.servico;

import java.util.List;
import br.upe.controlepesoapi.modelo.entidades.Usuario;


public interface IUsuarioServico {
  List<Usuario> listarUsuarios();

  Usuario incluirUsuario(Usuario usuario);

  Usuario alterarUsuario(Usuario usuario);

  void excluirUsuario(Long id);

  void adicionarPesoAoUsuario(String email, int peso);
}
