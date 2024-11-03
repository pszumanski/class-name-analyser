package pl.pszumanski.classnameanalyser.service.limit

import pl.pszumanski.classnameanalyser.dto.limit.LimitInfo

interface RateLimitService {
    fun getLimitInfo(): LimitInfo
}