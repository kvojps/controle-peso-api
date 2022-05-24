package br.upe.controlepesoapi.servico;

import java.util.List;

import br.upe.controlepesoapi.modelo.Usuario;


public interface IUsuarioServico {
	List<Usuario> listar();
	
	Usuario incluir(Usuario usuario);
	
	Usuario alterar(Usuario usuario, int altura, int pesoDesejado);
	
	void excluir(Long id);
}
