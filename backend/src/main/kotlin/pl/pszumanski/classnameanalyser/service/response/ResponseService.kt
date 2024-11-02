package pl.pszumanski.classnameanalyser.service.response

import pl.pszumanski.classnameanalyser.dto.api.ApiRequest

interface ResponseService {
    fun getResponse(apiRequest: ApiRequest)
}