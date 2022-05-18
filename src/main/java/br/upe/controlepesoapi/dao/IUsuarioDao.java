package br.upe.controlepesoapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.upe.controlepesoapi.modelo.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {
	Usuario findByEmailIgnoreCase(String email);
}
