package br.upe.controlepesoapi.controle;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.upe.controlepesoapi.modelo.dtos.RegistroPesoDTO;
import br.upe.controlepesoapi.modelo.dtos.UsuarioDTO;
import br.upe.controlepesoapi.modelo.entidades.RegistroPeso;
import br.upe.controlepesoapi.modelo.entidades.Usuario;
import br.upe.controlepesoapi.servico.UsuarioServico;
import lombok.Data;

@RequestMapping("/api")
@RestController
@CrossOrigin("*")
public class UsuarioControle {
	@Autowired
	private UsuarioServico servicoUsuario;

	@GetMapping("/usuarios")
	public ResponseEntity<?> listarUsuarios() {
		ResponseEntity<?> resposta = null;

		List<Usuario> usuarios = servicoUsuario.listarUsuarios();

		if (usuarios != null && !usuarios.isEmpty()) {
			List<UsuarioDTO> dtos = usuarios.stream().map(usuario -> getUsuarioDTO(usuario))
					.collect(Collectors.toList());

			resposta = ResponseEntity.ok().body(dtos);
		} else {
			resposta = ResponseEntity.noContent().build();
		}

		return resposta;
	}

	@PostMapping("/usuario/salvar")
	public ResponseEntity<UsuarioDTO> incluirUsuario(@RequestBody UsuarioDTO usuario) {
		Usuario registro = this.servicoUsuario.incluirUsuario(usuario.getUsuario());

		return new ResponseEntity<UsuarioDTO>(getUsuarioDTO(registro), HttpStatus.CREATED);
	}
	
	//Não está funcionando
	@PutMapping("/usuario/{id}")
	public ResponseEntity<UsuarioDTO> alterarUsuario(@RequestBody UsuarioDTO usuario) {
		Usuario registro = this.servicoUsuario.alterarUsuario(usuario.getUsuario());
		return ResponseEntity.ok().body(getUsuarioDTO(registro));
	}

	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<?> excluirUsuario(@Valid @PathVariable(value = "id") Long id) {
		servicoUsuario.excluirUsuario(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/usuario/addpeso")
	public ResponseEntity<?> addPesoParaUsuario(@RequestBody PesoParaUsuarioForm form) {
		servicoUsuario.adicionarPesoAoUsuario(form.getEmail(), form.getPeso());
		return ResponseEntity.ok().build();
	}

	private UsuarioDTO getUsuarioDTO(Usuario usuario) {
		List<RegistroPesoDTO> registrosPeso = null;

		if (usuario.getRegistrosPeso() != null && !usuario.getRegistrosPeso().isEmpty()) {
			registrosPeso = usuario.getRegistrosPeso().stream()
					.map(registro -> RegistroPesoDTO.builder().id(registro.getId()).peso(registro.getPeso())
							.data(registro.getData()).idUsuario(usuario.getId()).build())
					.collect(Collectors.toList());
		}

		return UsuarioDTO.builder().id(usuario.getId()).nome(usuario.getNome()).email(usuario.getEmail())
				.altura(usuario.getAltura()).sexo(usuario.getSexo()).pesoDesejado(usuario.getPesoDesejado())
				.registrosPeso(registrosPeso).build();
	}
}

@Data
class PesoParaUsuarioForm {
	private String email;
	private int peso;
}
