package pl.pszumanski.classnameanalyser.dto.limit

import com.fasterxml.jackson.annotation.JsonProperty

data class LimitResources(@JsonProperty("code_search") val codeSearch: LimitInfo)