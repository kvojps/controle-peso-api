package br.upe.controlepesoapi.servico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.upe.controlepesoapi.modelo.entidades.RegistroPeso;
import br.upe.controlepesoapi.modelo.entidades.Usuario;
import br.upe.controlepesoapi.repositorio.IRegistroPesoRepositorio;
import br.upe.controlepesoapi.repositorio.IUsuarioRepositorio;

@Service
public class UsuarioServico implements IUsuarioServico {

	@Autowired
	private IUsuarioRepositorio usuarioRepositorio;
	@Autowired
	private IRegistroPesoRepositorio registroDao;
	private UsuarioValidacaoServico validacao;

	@Override
	public List<Usuario> listar() {
		return (List<Usuario>) usuarioRepositorio.findAll();
	}

	@Override
	public Usuario incluir(Usuario usuario) {
		validacao.validarInclusaoUsuario(usuario);
		return usuarioRepositorio.save(usuario);
	}

	@Override
	public Usuario alterar(Usuario usuario) {
		validacao.validarAlteracaoUsuario(usuario);
		return usuarioRepositorio.save(usuario);
	}

	@Override
	public void excluir(Long id) {
		validacao.validarExclusaoUsuario(id);
		usuarioRepositorio.deleteById(id);
	}

	public void adicionarPesoAoUsuario(String email, RegistroPeso peso) {
		//Falta a validação
		Optional<Usuario> usuario = usuarioRepositorio.findByEmailIgnoreCase(email);
		peso.setUsuario(usuario.get());
		peso.setData(LocalDateTime.now());
		registroDao.save(peso);
		usuario.get().getRegistrosPeso().add(peso);
	}
	
	public int getPesoAtual(Usuario usuario) {
		RegistroPeso registroAtual = registroDao.findByUsuario(usuario);
		return registroAtual.getPeso();
	}
	
	public Usuario getUsuarioByEmail(String email) {
		Optional<Usuario> usuario = usuarioRepositorio.findByEmailIgnoreCase(email);
		validacao.obterViolacoes(usuario.get());
		return usuario.get();
	}
}
