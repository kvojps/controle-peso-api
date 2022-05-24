package br.upe.controlepesoapi.controle;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.upe.controlepesoapi.modelo.RegistroPeso;
import br.upe.controlepesoapi.modelo.Usuario;
import br.upe.controlepesoapi.servico.UsuarioServico;
import lombok.Data;

@RequestMapping("/api")
@RestController
public class UsuarioControle {
	private UsuarioServico servicoUsuario;

	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> getUsuarios() {
		return ResponseEntity.ok().body(servicoUsuario.listar());
	}

	@PostMapping("/usuario/salvar")
	public ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario) {
		URI uri = URI
				.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/usuario/salvar").toUriString());
		return ResponseEntity.created(uri).body(servicoUsuario.incluir(usuario));
	}
	
	@PutMapping("/usuario/{id}")
	public ResponseEntity<Usuario> alterarUsuario(@RequestBody Usuario usuario) {
		return ResponseEntity.ok().body(servicoUsuario.alterar(usuario));
	}
	
	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<?> excluir(@Valid @PathVariable(value = "id") Long id) {
		servicoUsuario.excluir(id);
		return ResponseEntity.ok().build();
	}
	
	public ResponseEntity<?> addPesoParaUsuario(@RequestBody PesoParaUsuarioForm form) {
		servicoUsuario.adicionarPesoAoUsuario(form.getEmail(), form.getPeso());
		return ResponseEntity.ok().build();
	}
}

@Data
class PesoParaUsuarioForm {
	private String email;
	private RegistroPeso peso;
}
