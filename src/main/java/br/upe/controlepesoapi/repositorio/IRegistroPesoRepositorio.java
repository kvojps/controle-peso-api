package br.upe.controlepesoapi.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.upe.controlepesoapi.modelo.RegistroPeso;
import br.upe.controlepesoapi.modelo.Usuario;

public interface IRegistroPesoRepositorio extends JpaRepository<RegistroPeso, Long> {
	RegistroPeso findByUsuario(Usuario usuario);
}
