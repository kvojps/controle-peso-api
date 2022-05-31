package br.upe.controlepesoapi.servico;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.util.StringUtils;

import br.upe.controlepesoapi.excecao.ControlePesoException;
import br.upe.controlepesoapi.excecao.NaoEncontradoException;
import br.upe.controlepesoapi.modelo.Usuario;
import br.upe.controlepesoapi.repositorio.IUsuarioRepositorio;
import lombok.extern.slf4j.Slf4j;

public class UsuarioValidacaoServico {
	private Validator validator;
	private IUsuarioRepositorio usuarioRepositorio;

	public UsuarioValidacaoServico() {
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	public void validarInclusaoUsuario(Usuario usuario) {
		validarUsuario(usuario);

		Optional<Usuario> existente = usuarioRepositorio.findByEmailIgnoreCase(usuario.getEmail());

		if (existente.isPresent()) {
			throw new ControlePesoException(
					"Ocorreu um erro ao alterar o usuário: já existe usuário cadastrado com esse e-mail: "
							+ usuario.getEmail());
		}
	}

	public void validarAlteracaoUsuario(Usuario usuario) {
		validarUsuario(usuario);

		if (usuario.getId() == 0L) {
			throw new ControlePesoException("Ocorreu um erro ao alterar o usuário: informe o identificador");
		}

		Optional<Usuario> anterior = usuarioRepositorio.findById(usuario.getId());

		if (!anterior.isPresent()) {
			throw new NaoEncontradoException("Ocorreu um erro ao alterar o usuário : usuário não encontrado");
		}

		Optional<Usuario> usuarioExistente = usuarioRepositorio.findByEmailIgnoreCase(usuario.getEmail());

		if (usuarioExistente.isPresent()) {
			throw new ControlePesoException(
					"Ocorreu um erro ao alterar o usuário: já existe usuário cadastrado com esse e-mail: "
							+ usuario.getEmail());
		}
	}

	public void validarExclusaoUsuario(Long id) {
		if (id == null) {
			throw new ControlePesoException("Ocorreu um erro ao excluir usuário: informe o identificador");
		}

		if (!usuarioRepositorio.existsById(id)) {
			throw new NaoEncontradoException("Ocorreu um erro ao excluir usuário: Usuário não encontrado");
		}
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

	public void validarUsuario(Usuario usuario) {
		String erros = this.obterViolacoes(usuario);

		if (StringUtils.hasText(erros)) {
			throw new ControlePesoException("Ocorreu um erro ao incluir usuário: " + erros);
		}
	}
}
