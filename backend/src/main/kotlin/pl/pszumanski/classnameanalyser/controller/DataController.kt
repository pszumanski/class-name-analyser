package pl.pszumanski.classnameanalyser.controller

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import pl.pszumanski.classnameanalyser.dto.api.ApiRequest
import pl.pszumanski.classnameanalyser.service.response.ResponseService

@Controller
class DataController(val responseService: ResponseService) {

    @MessageMapping("/init")
    fun getData(request: ApiRequest) {
        responseService.getResponse(request)
    }
}