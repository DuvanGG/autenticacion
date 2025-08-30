package co.com.pragma.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.pragma.api.dto.UsuarioRequestDTO;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({
        @RouterOperation(
            path = "/api/v1/usuarios",
            method = RequestMethod.POST,
            beanClass = Handler.class,
            beanMethod = "registrarUsuario",
            operation = @Operation(
                operationId = "registrarUsuario",
                summary = "Registrar un usuario",
                description = "Crea un nuevo usuario en el sistema",
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos del usuario a registrar",
                    content = @Content(schema = @Schema(implementation = UsuarioRequestDTO.class))
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        description = "Usuario registrado correctamente",
                        content = @Content(schema = @Schema(implementation = UsuarioRequestDTO.class))
                    ),
                    @ApiResponse(
                        responseCode = "400",
                        description = "Datos inválidos"
                    )
                }
            )
        )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/usecase/path"), handler::listenGETUseCase)
                .andRoute(POST("/api/usecase/otherpath"), handler::listenPOSTUseCase)
                .and(route(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase))
                .and(route(POST("/api/v1/usuarios"), handler::registrarUsuario));
    }
}
