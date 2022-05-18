package br.upe.controlepesoapi.servico;

import java.util.List;

import br.upe.controlepesoapi.modelo.RegistroPeso;

public interface IRegistroPesoServico {
	List<RegistroPeso> listar(Long idUsuario);
	
	RegistroPeso incluir(RegistroPeso registroPeso);
	
	RegistroPeso alterar(RegistroPeso registroPeso);
	
	void excluir(Long id);
}
