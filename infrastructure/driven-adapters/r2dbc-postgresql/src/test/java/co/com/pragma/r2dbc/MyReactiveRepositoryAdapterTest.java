package co.com.pragma.r2dbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;

import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.r2dbc.entity.UsuarioEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyReactiveRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    MyReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    MyReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    void mustFindValueById() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(1L);
        entity.setCorreoElectronico("test@mail.com");

        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico("test@mail.com");

        when(repository.findById(1L)).thenReturn(Mono.just(entity));
        when(mapper.map(entity, Usuario.class)).thenReturn(usuario);

        Mono<Usuario> result = repositoryAdapter.findById(1L);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getCorreoElectronico().equals("test@mail.com"))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(1L);
        entity.setCorreoElectronico("test@mail.com");

        Usuario domain = new Usuario();
        domain.setId(1L);
        domain.setCorreoElectronico("test@mail.com");

        // Mock del repositorio (devuelve entidades)
        when(repository.findAll()).thenReturn(Flux.just(entity));

        // Mock del mapper (convierte entidad -> dominio)
        when(mapper.map(entity, Usuario.class)).thenReturn(domain);

        // Llamamos al método del adapter
        Flux<Usuario> result = repositoryAdapter.findAll();

        // Verificamos con StepVerifier
        StepVerifier.create(result)
                .expectNextMatches(value -> value.getCorreoElectronico().equals("test@mail.com"))
                .verifyComplete();
    }

    @Test
    void mustFindByExample() {
        Usuario example = new Usuario(); // dominio, no entidad
        UsuarioEntity entity = new UsuarioEntity(); // entidad simulada

        when(repository.findAll(any(Example.class))).thenReturn(Flux.just(entity));
        when(mapper.map(entity, Usuario.class)).thenReturn(example);

        Flux<Usuario> result = repositoryAdapter.findByExample(example);

        StepVerifier.create(result)
                .expectNext(example)
                .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        Usuario domain = new Usuario(1L, "test", null, null, null, null, null, null);
        UsuarioEntity entity = new UsuarioEntity();

        // mock del mapper dominio -> entidad
        when(mapper.map(domain, UsuarioEntity.class)).thenReturn(entity);

        // mock del save
        when(repository.save(entity)).thenReturn(Mono.just(entity));

        // mock del mapper entidad -> dominio
        when(mapper.map(entity, Usuario.class)).thenReturn(domain);

        // ejecutar el método
        Mono<Usuario> result = repositoryAdapter.save(domain);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(domain))
                .verifyComplete();
    }
}
