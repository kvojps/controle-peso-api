package br.upe.controlepesoapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.upe.controlepesoapi.modelo.Usuario;

public interface IUsuario extends JpaRepository<Usuario, Long> {
	Usuario findByEmailIgnoreCase(String email);
}
