package dev.pegorari.rarehub.controller

import dev.pegorari.rarehub.dto.ProductRequest
import dev.pegorari.rarehub.service.FichaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/fci")
class FichaController(private val service: FichaService) {

    @GetMapping
    fun teste(@RequestBody products : List<ProductRequest>): Mono<String> {
        return service.getBearerToken()
            .flatMap { login ->
                val bearerToken = login.bearerToken
                service.getFCI(bearerToken!!, products)
                    .flatMap {
                        service.getVisualizaFCI(bearerToken)
                        // logout
                    }
            }

    }
}