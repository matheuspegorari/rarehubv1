package dev.pegorari.rarehub.service

import com.fasterxml.jackson.databind.ObjectMapper
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
                headers.set("token", Credentials.TOKEN)
                headers.set("appkey", Credentials.APPKEY)
                headers.set("username", Credentials.USERNAME)
                headers.set("password", Credentials.PASSWORD)
            }
            .retrieve()
            .bodyToMono(LoginResponse::class.java)
    }

    fun getFCI(bearer: String, listaProdutos: List<ProductRequest>): Mono<String> {
        println("Chamando getFCI...")
        val objectMapper = ObjectMapper()
        val listaProdutosJson: String = objectMapper.writeValueAsString(listaProdutos)
        val requestBody = """
            {
          "serviceName": "FichaConteudoImportacaoSP.gerarArquivo",
          "requestBody": {
            "produtos": {
              "chaveArquivo": "ARQUIVO_FCI_GERADO",
              "codemp": 1,
              "prod": $listaProdutosJson
            }
          }
        }
        """.trimIndent()
        return webClient.post()
            .uri("https://api.sankhya.com.br/gateway/v1/mgecom/service.sbr?serviceName=FichaConteudoImportacaoSP.gerarArquivo&application=FichaConteudoImportacao&outputType=json")
            .headers { headers ->
                headers.set("Authorization", "Bearer $bearer")
            }
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String::class.java)
            .flatMap { response -> checkForErrors(response) }
    }

    fun getVisualizaFCI(bearer: String): Mono<String> {
        return webClient.post()
            .uri("https://api.sankhya.com.br/gateway/v1/mge/visualizadorArquivos.mge?hidemail=S&forcarDownload=S&chaveArquivo=ARQUIVO_FCI_GERADO")
            .headers { headers ->
                headers.set("Authorization", "Bearer $bearer")
            }
            .bodyValue("")
            .retrieve()
            .onStatus({ status -> !status.is2xxSuccessful }) { response ->
                response.createException().flatMap { Mono.error(it) }
            }
            .bodyToMono(String::class.java)
    }

    fun checkForErrors(responseString: String): Mono<String> {
        val objectMapper = ObjectMapper()
        val responseJson = try {
            objectMapper.readTree(responseString)
        } catch (e: Exception) {
            return Mono.error(RuntimeException("Erro ao processar resposta: $responseString"))
        }
        val status = responseJson.get("status")

        if (status != null && status.asText() == "0") {
            val statusMessage = responseJson.get("statusMessage")?.asText() ?: responseString
            return Mono.error(RuntimeException("Erro: $statusMessage"))
        }

        return Mono.just(responseString)
    }

}