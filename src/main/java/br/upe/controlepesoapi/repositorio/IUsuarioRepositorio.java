package br.upe.controlepesoapi.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.upe.controlepesoapi.modelo.entidades.Usuario;

public interface IUsuarioRepositorio extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmailIgnoreCase(String email);
}
