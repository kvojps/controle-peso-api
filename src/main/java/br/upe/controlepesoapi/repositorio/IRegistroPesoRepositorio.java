package br.upe.controlepesoapi.repositorio;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.upe.controlepesoapi.modelo.entidades.RegistroPeso;
import br.upe.controlepesoapi.modelo.entidades.Usuario;

public interface IRegistroPesoRepositorio extends JpaRepository<RegistroPeso, Long> {
  RegistroPeso findByUsuario(Usuario usuario);

  public Optional<RegistroPeso> findFirstByUsuarioEmailOrderByDataDesc(String email);

  public List<RegistroPeso> findByUsuarioEmailOrderByDataAsc(String email);
}
