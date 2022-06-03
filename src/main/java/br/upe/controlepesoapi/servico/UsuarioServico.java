package br.upe.controlepesoapi.servico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.upe.controlepesoapi.excecao.ControlePesoException;
import br.upe.controlepesoapi.modelo.entidades.RegistroPeso;
import br.upe.controlepesoapi.modelo.entidades.Usuario;
import br.upe.controlepesoapi.repositorio.IRegistroPesoRepositorio;
import br.upe.controlepesoapi.repositorio.IUsuarioRepositorio;

@Service
public class UsuarioServico implements IUsuarioServico {

	@Autowired
	private IUsuarioRepositorio usuarioRepositorio;
	@Autowired
	private IRegistroPesoRepositorio registroPesoRepositorio;

	@Override
	public List<Usuario> listarUsuarios() {
		return (List<Usuario>) usuarioRepositorio.findAll();
	}

	@Override
	public Usuario incluirUsuario(Usuario usuario) {
		return usuarioRepositorio.save(usuario);
	}

	@Override
	public Usuario alterarUsuario(Usuario usuario) {
		return usuarioRepositorio.save(usuario);
	}

	@Override
	public void excluirUsuario(Long id) {
		usuarioRepositorio.deleteById(id);
	}

	public void adicionarPesoAoUsuario(String email, int peso) {
		Optional<Usuario> usuario = usuarioRepositorio.findByEmailIgnoreCase(email);
		RegistroPeso pesoNovo = new RegistroPeso();
		pesoNovo.setUsuario(usuario.get());
		pesoNovo.setData(LocalDateTime.now());
		pesoNovo.setPeso(peso);
		registroPesoRepositorio.save(pesoNovo);
		usuario.get().getRegistrosPeso().add(pesoNovo);
	}
	
	public int getPesoAtual(Usuario usuario) {
		RegistroPeso registroAtual = registroPesoRepositorio.findByUsuario(usuario);
		return registroAtual.getPeso();
	}
}
