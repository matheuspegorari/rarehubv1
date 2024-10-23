package dev.pegorari.rarehub.enums

enum class Credentials {
    TOKEN,
    USERNAME,
    PASSWORD;

    companion object {
        val TOKEN: String = System.getenv("API_TOKEN")
        val APPKEY: String = System.getenv("API_APPKEY")
        val USERNAME: String = System.getenv("API_USERNAME")
        val PASSWORD: String = System.getenv("API_PASSWORD")
    }
}