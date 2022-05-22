package br.upe.controlepesoapi.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.upe.controlepesoapi.modelo.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmailIgnoreCase(String email);
}
