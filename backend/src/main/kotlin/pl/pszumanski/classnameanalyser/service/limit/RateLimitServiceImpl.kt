package pl.pszumanski.classnameanalyser.service.limit

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import pl.pszumanski.classnameanalyser.dto.limit.LimitInfo
import pl.pszumanski.classnameanalyser.dto.limit.LimitResponse

@Service
class RateLimitServiceImpl(val githubClient: RestClient) : RateLimitService {

    override fun getLimitInfo(): LimitInfo {
        return getLimitResponse().resources.codeSearch
    }

    fun getLimitResponse(): LimitResponse = githubClient.get()
        .uri("rate_limit")
        .retrieve()
        .body<LimitResponse>() ?: throw IllegalStateException("Fetching error")
}