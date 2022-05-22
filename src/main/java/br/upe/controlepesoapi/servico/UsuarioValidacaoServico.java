package br.upe.controlepesoapi.servico;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.util.StringUtils;

import br.upe.controlepesoapi.dao.IUsuarioDao;
import br.upe.controlepesoapi.modelo.Usuario;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsuarioValidacaoServico {
	private Validator validator;
	private IUsuarioDao usuarioDao;
	
	public UsuarioValidacaoServico() {
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	public String obterViolacoes(Usuario usuario) {
		String mensagem = null;

		if (usuario == null) {
			mensagem = "dados nulos";
		} else {
			mensagem = this.validator.validate(usuario).stream()
					.map(cv -> cv == null ? "null" : cv.getPropertyPath() + ": " + cv.getMessage())
					.collect(Collectors.joining(", "));
		}

		return mensagem;
	}

	public Boolean validarInclusaoUsuario(Usuario usuario) {
		String erros = this.obterViolacoes(usuario);

		if (StringUtils.hasText(erros)) {
			log.error("Erro ao incluir usuário");
			return false;
		}

		Optional<Usuario> existente = usuarioDao.findByEmailIgnoreCase(usuario.getEmail());

		if (existente.isPresent()) {
			log.error("Usuário já cadastrado");
			return false;
		}

		return true;
	}

	public Boolean validarAlteracaoUsuario(Usuario usuario) {
		String erros = this.obterViolacoes(usuario);

		if (StringUtils.hasText(erros)) {
			log.error("Erro ao alterar usuário");
			return false;
		}

		Optional<Usuario> anterior = usuarioDao.findById(usuario.getId());

		if (!anterior.isPresent()) {
			log.error("Usuário não existe");
			return false;
		}

		Optional<Usuario> usuarioExistente = usuarioDao.findByEmailIgnoreCase(usuario.getEmail());

		if (usuarioExistente.isPresent()) {
			log.error("erro ao editar usuário");
			return false;
		}

		return true;
	}

	public Boolean validarExclusaoUsuario(Long id) {
		if (!usuarioDao.existsById(id)) {
			log.error("Usuário não existe");
			return false;
		}
		
		return true;
	}
}
