package br.upe.controlepesoapi.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.upe.controlepesoapi.repositorio.IRegistroPesoRepositorio;
import br.upe.controlepesoapi.repositorio.IUsuarioRepositorio;

@Service
public class PesoServico {
  @Autowired
  private IRegistroPesoRepositorio pesoRepositorio;

  @Autowired
  private IUsuarioRepositorio usuarioRepositorio;


}
