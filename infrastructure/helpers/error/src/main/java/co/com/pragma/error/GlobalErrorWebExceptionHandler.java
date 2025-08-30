package co.com.pragma.error;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {


	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		
		if (exchange.getResponse().isCommitted()) {
			return Mono.error(ex);
		}

		var response = exchange.getResponse();
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

		String errorMessage;
		HttpStatus status;

		if (ex instanceof IllegalArgumentException) {
			status = HttpStatus.BAD_REQUEST; // 400
			errorMessage = ex.getMessage();
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
			errorMessage = "Ocurrió un error inesperado";
		}

		response.setStatusCode(status);

		// revisar
		byte[] bytes = ("{\"error\": \"" + errorMessage + "\"}").getBytes(StandardCharsets.UTF_8);

		return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
	}

}