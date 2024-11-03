package pl.pszumanski.classnameanalyser.dto.limit

import com.fasterxml.jackson.annotation.JsonProperty

data class LimitInfo(
    @JsonProperty("remaining") private val _remaining: Int,
    val reset: Long,
) {
    val remaining: Int
        get() = _remaining - 1 // GitHub Api blocks last request
}