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

import br.upe.controlepesoapi.dao.IRegistroPesoDao;
import br.upe.controlepesoapi.dao.IUsuarioDao;
import br.upe.controlepesoapi.modelo.RegistroPeso;
import br.upe.controlepesoapi.modelo.Usuario;
import lombok.extern.slf4j.Slf4j;

@Service
public class UsuarioServico implements IUsuarioServico {

	@Autowired
	private IUsuarioDao usuarioDao;
	@Autowired
	private IRegistroPesoDao registroDao;
	private UsuarioValidacaoServico validacao;

	@Override
	public List<Usuario> listar() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	public Usuario incluir(Usuario usuario) {

		if (validacao.validarInclusaoUsuario(usuario) == false) {
			return null;
		}
		return usuarioDao.save(usuario);
	}

	@Override
	public Usuario alterar(Usuario usuario, Usuario usuarioEditado) {
		if (validacao.validarAlteracaoUsuario(usuario) == false) {
			return null;
		}
		return usuarioDao.save(usuarioEditado);
	}

	@Override
	public void excluir(Long id) {
		if (validacao.validarExclusaoUsuario(id) == false) {
			return;
		}
		usuarioDao.deleteById(id);
	}

	public void adicionarPesoAoUsuario(String email, RegistroPeso peso) {
		//Falta a validação.
		Optional<Usuario> usuario = usuarioDao.findByEmailIgnoreCase(email);
		peso.setUsuario(usuario.get());
		peso.setData(LocalDateTime.now());
		registroDao.save(peso);
		usuario.get().getRegistrosPeso().add(peso);
	}
}
