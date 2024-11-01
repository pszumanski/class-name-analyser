package pl.pszumanski.classnameanalyser.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.pszumanski.classnameanalyser.dto.ApiResponse
import pl.pszumanski.classnameanalyser.service.response.ResponseService
import pl.pszumanski.classnameanalyser.values.SupportedLanguage

@RestController
@RequestMapping("/public/api/v1/data")
class ClassDataController(val responseService: ResponseService) {

    @GetMapping
    fun getClassData(@RequestParam(required = false) language: String): ApiResponse = responseService
        .getResponse(language = enumValueOf(language.uppercase()))
}