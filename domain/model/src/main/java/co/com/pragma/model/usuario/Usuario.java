package co.com.pragma.model.usuario;
import lombok.Builder;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Usuario {
	
	private Long id;
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
	@Schema(description = "Correo electrónico válido", example = "nombre2.apellido@email.com")
    private String correoElectronico;
	@Schema(description = "Salario base", example = "15000000")
    private Double salarioBase;
}
