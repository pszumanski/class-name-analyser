package pl.pszumanski.classnameanalyser.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.pszumanski.classnameanalyser.dto.limit.LimitInfo
import pl.pszumanski.classnameanalyser.service.limit.RateLimitService

@RestController
@RequestMapping("/rate-limit")
@CrossOrigin
class RateLimitController(val rateLimitService: RateLimitService) {

    @GetMapping
    fun getRateLimit(): LimitInfo = rateLimitService.getLimitInfo()
}