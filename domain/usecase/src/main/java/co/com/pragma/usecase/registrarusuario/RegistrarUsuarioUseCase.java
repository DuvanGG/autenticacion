package co.com.pragma.usecase.registrarusuario;

import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
public class RegistrarUsuarioUseCase {
	
	private final UsuarioRepository usuarioRepository;

	public Mono<Usuario> registrarUsuario(Usuario usuario) {
		// Validaciones de campos obligatorios
	    if (usuario.getNombres() == null || usuario.getNombres().isBlank() ||
	        usuario.getApellidos() == null || usuario.getApellidos().isBlank() ||
	        usuario.getCorreoElectronico() == null || usuario.getCorreoElectronico().isBlank() ||
	        usuario.getSalarioBase() == null) {
	        return Mono.error(new IllegalArgumentException("Los campos nombres, apellidos, correo electrónico y salario base son obligatorios"));
	    }

	    // Validación de formato de salario_base (0 a 15'000.000)
	    if (usuario.getSalarioBase() < 0 || usuario.getSalarioBase() > 15_000_000) {
	        return Mono.error(new IllegalArgumentException("El salario_base debe estar entre 0 y 15.000.000"));
	    }

	    // Validación de formato de correo electrónico
	    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	    if (!usuario.getCorreoElectronico().matches(emailRegex)) {
	        return Mono.error(new IllegalArgumentException("El correo electrónico no tiene un formato válido"));
	    }

	    // Validación de unicidad de correo
	    return usuarioRepository.findByCorreoElectronico(usuario.getCorreoElectronico())
	        .flatMap(u -> Mono.<Usuario>error(new IllegalArgumentException("El correo ya está registrado")))
	        .switchIfEmpty(usuarioRepository.save(usuario));
	}
}
