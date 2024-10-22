package dev.pegorari.rarehub.controller

import dev.pegorari.rarehub.dto.ProductRequest
import dev.pegorari.rarehub.model.LoginResponse
import dev.pegorari.rarehub.service.FichaService
import org.apache.coyote.http11.Constants.a
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/fci")
class FichaController(private val service: FichaService) {


    @GetMapping
    fun generateFci(@RequestBody products: List<ProductRequest>) {
        service.getBearerToken().flatMap { response ->
            val bearerToken: String? = response.bearerToken
            if (bearerToken != null) {

            } else {
                Mono.just(ResponseEntity.status(400).body("Erro ao realizar Login"))
            }

        }



    }
}