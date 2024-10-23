package dev.pegorari.rarehub.controller

import dev.pegorari.rarehub.dto.ProductRequest
import dev.pegorari.rarehub.service.FichaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/fci")
class FichaController(private val service: FichaService) {

    @PostMapping
    fun getFCIFromProducts(@RequestBody products: List<ProductRequest>): Mono<ResponseEntity<String>> {
        return service.getBearerToken()
            .flatMap { login ->
                val bearerToken = login.bearerToken
                service.getFCI(bearerToken!!, products)
                    .flatMap {
                        service.getVisualizaFCI(bearerToken)
                    }
            }
            .map { responseBody ->
                ResponseEntity.ok(responseBody)
            }
            .onErrorResume { ex ->
                // Retorna uma resposta de erro com status adequado
                Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: ${ex.message}"))
            }
    }
}