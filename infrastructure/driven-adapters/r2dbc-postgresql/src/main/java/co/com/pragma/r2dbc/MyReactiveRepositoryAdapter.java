package co.com.pragma.r2dbc;

import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.UsuarioRepository;
import co.com.pragma.r2dbc.entity.UsuarioEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import reactor.core.publisher.Mono;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    Usuario/* change for domain model */,
    UsuarioEntity/* change for adapter model */,
    Long,
    MyReactiveRepository
> implements UsuarioRepository{
	
	
    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Usuario.class/* change for domain model */));
    }
    

    /**
     * Guardar usuario en bd.
     */
    @Override
    @Transactional
    public Mono<Usuario> save(Usuario usuario) {
        UsuarioEntity entity = UsuarioEntity.fromDomain(usuario); // convertir dominio → entidad
        return repository.save(entity)
                .map(UsuarioEntity::toDomain); // entidad → dominio
    }

    /**
     * Buscar usuario por correo.
     */
    public Mono<Usuario> findByCorreoElectronico(String correoElectronico) {
        return repository.findByCorreoElectronico(correoElectronico)
                .map(this::mapToDomain);
    }

    // ---------------------------
    // Métodos privados de mapeo
    // ---------------------------
    private UsuarioEntity mapToEntity(Usuario u) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(u.getId());
        entity.setNombres(u.getNombres());
        entity.setApellidos(u.getApellidos());
        entity.setFechaNacimiento(u.getFechaNacimiento());
        entity.setDireccion(u.getDireccion());
        entity.setTelefono(u.getTelefono());
        entity.setCorreoElectronico(u.getCorreoElectronico());
        entity.setSalarioBase(u.getSalarioBase());
        return entity;
    }

    private Usuario mapToDomain(UsuarioEntity e) {
        return Usuario.builder()
                .id(e.getId())
                .nombres(e.getNombres())
                .apellidos(e.getApellidos())
                .fechaNacimiento(e.getFechaNacimiento())
                .direccion(e.getDireccion())
                .telefono(e.getTelefono())
                .correoElectronico(e.getCorreoElectronico())
                .salarioBase(e.getSalarioBase())
                .build();
    }

}
