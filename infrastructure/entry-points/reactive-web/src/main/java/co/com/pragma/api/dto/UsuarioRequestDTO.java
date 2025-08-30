package co.com.pragma.api.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UsuarioRequestDTO {
	@Schema(description = "Nombres del usuario", example = "NombreA")
	private String nombres;

	@Schema(description = "Apellidos del usuario", example = "ApellidoA")
	private String apellidos;

	@Schema(description = "Fecha de nacimiento", example = "2024-05-15")
	private LocalDate fechaNacimiento;

	@Schema(description = "Dirección de residencia", example = "Calle 890")
	private String direccion;

	@Schema(description = "Teléfono de contacto", example = "300321456")
	private String telefono;

	@Schema(description = "Correo electrónico válido", example = "nombre.apellido@email.com")
	private String correoElectronico;

	@Schema(description = "Salario base", example = "15000000")
	private Double salarioBase;

}
