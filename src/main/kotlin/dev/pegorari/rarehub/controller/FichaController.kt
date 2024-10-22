package dev.pegorari.rarehub.controller

import dev.pegorari.rarehub.service.FichaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/fci")
class FichaController (private val service: FichaService) {



    //get method, hello world
    @GetMapping
    fun generateFci() {

    }
}