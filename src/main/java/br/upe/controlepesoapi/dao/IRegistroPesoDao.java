package br.upe.controlepesoapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.upe.controlepesoapi.modelo.RegistroPeso;
import br.upe.controlepesoapi.modelo.Usuario;

public interface IRegistroPesoDao extends JpaRepository<RegistroPeso, Long> {
	RegistroPeso findByUsuario(Usuario usuario);
}
