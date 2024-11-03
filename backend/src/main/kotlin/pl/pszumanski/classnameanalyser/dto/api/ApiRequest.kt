package pl.pszumanski.classnameanalyser.dto.api

data class ApiRequest(
    val language: String,
    val sessionId: String,
)