package pl.pszumanski.classnameanalyser.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class ClientConfig {

    @Value("\${github.personal-access-token}")
    lateinit var githubToken:String

    @Bean
    fun githubClient(): RestClient = RestClient.builder()
        .baseUrl("https://api.github.com")
        .defaultHeader("Accept", "application/json")
        .defaultHeader("Authorization", "token $githubToken")
        .defaultHeader("User-Agent", "Class Name Analyser")
        .build()
}