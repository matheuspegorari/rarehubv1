package dev.pegorari.rarehub.service

import dev.pegorari.rarehub.dto.ProductRequest
import dev.pegorari.rarehub.enums.Credentials
import dev.pegorari.rarehub.model.LoginResponse
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class FichaService {
    private val webClient: WebClient = WebClient.create()

    fun getBearerToken(): Mono<LoginResponse> {
        return webClient.post()
            .uri("https://api.sankhya.com.br/login")
            .headers { headers ->
                headers.set("token", "Credentials.TOKEN")
                headers.set("appkey", Credentials.APPKEY)
                headers.set("username", Credentials.USERNAME)
                headers.set("password", Credentials.PASSWORD)
            }
            .retrieve()
            .bodyToMono(LoginResponse::class.java)
    }


}