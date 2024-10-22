package dev.pegorari.rarehub

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RarehubApplication

fun main(args: Array<String>) {
    runApplication<RarehubApplication>(*args)
}
