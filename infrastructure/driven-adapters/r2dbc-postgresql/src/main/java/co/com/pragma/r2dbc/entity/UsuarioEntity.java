package co.com.pragma.r2dbc.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import co.com.pragma.model.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("Usuario")
public class UsuarioEntity {
    
    @Id
    private Long id;  // Si es autogenerado en BD, ponlo como Long/Integer y deja null al crear
    
    private String nombres;
    private String apellidos;
    
    @Column("fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    private String direccion;
    private String telefono;
    
    @Column("correo_electronico")
    private String correoElectronico;
    
    @Column("salario_base")
    private Double salarioBase;

    // De Dominio -> Entidad
    public static UsuarioEntity fromDomain(Usuario usuario) {
        return new UsuarioEntity(
            usuario.getId(),
            usuario.getNombres(),
            usuario.getApellidos(),
            usuario.getFechaNacimiento(),
            usuario.getDireccion(),
            usuario.getTelefono(),
            usuario.getCorreoElectronico(),
            usuario.getSalarioBase()
        );
    }

    // De Entidad -> Dominio
    public Usuario toDomain() {
        return new Usuario(
            this.id,
            this.nombres,
            this.apellidos,
            this.fechaNacimiento,
            this.direccion,
            this.telefono,
            this.correoElectronico,
            this.salarioBase
        );
    }
}
