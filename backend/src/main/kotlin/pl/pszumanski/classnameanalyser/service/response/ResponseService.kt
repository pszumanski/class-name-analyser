package pl.pszumanski.classnameanalyser.service.response

import pl.pszumanski.classnameanalyser.dto.ApiResponse
import pl.pszumanski.classnameanalyser.values.SupportedLanguage

interface ResponseService {
    fun getResponse(language: SupportedLanguage = SupportedLanguage.JAVA): ApiResponse
}