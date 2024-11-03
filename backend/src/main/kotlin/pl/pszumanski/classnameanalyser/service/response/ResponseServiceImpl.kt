package pl.pszumanski.classnameanalyser.service.response

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import pl.pszumanski.classnameanalyser.dto.api.ApiRequest
import pl.pszumanski.classnameanalyser.dto.file.ClassesResponse
import pl.pszumanski.classnameanalyser.dto.repo.RepositoriesResponse
import pl.pszumanski.classnameanalyser.service.analysis.AnalysisService
import pl.pszumanski.classnameanalyser.service.data.DataService
import pl.pszumanski.classnameanalyser.service.limit.RateLimitService
import pl.pszumanski.classnameanalyser.values.SupportedLanguage
import pl.pszumanski.classnameanalyser.websocket.DataContainer
import pl.pszumanski.classnameanalyser.websocket.NotificationService

@Service
class ResponseServiceImpl(
    val dataService: DataService,
    val rateLimitService: RateLimitService,
    val analysisService: AnalysisService,
    val dataContainer: DataContainer,
    val notificationService: NotificationService,
) : ResponseService {

    val logger: Logger = LoggerFactory.getLogger(ResponseServiceImpl::class.java)

    override fun getResponse(apiRequest: ApiRequest) {
        val language = SupportedLanguage.fromString(apiRequest.language)
        val sessionId: String = apiRequest.sessionId

        var availableRequests = rateLimitService.getLimitInfo().remaining

        if (availableRequests == 0) return

        if (dataContainer.isEmpty()) {
            val repositoriesResponse: RepositoriesResponse = dataService.getPopularRepositories(language)

            dataContainer.sessionId = sessionId
            dataContainer.language = language
            dataContainer.repositories = repositoriesResponse.repositories
            dataContainer.totalRepositoriesCount = repositoriesResponse.publicRepositoriesCount
            availableRequests--
        }

        try {
            for (i in 0..<availableRequests) {
                val classesResponse: ClassesResponse = dataService.getClassesFromRepository(
                    repository = dataContainer.getRepositoryToAnalyse(),
                    language = language,
                    page = dataContainer.getPageToAnalyse()
                )

                dataContainer.repositoriesAnalysed++
                dataContainer.classesAnalysed += classesResponse.classes.size

                analysisService.analyse(classesResponse.classes, dataContainer)
                notificationService.update(sessionId, dataContainer.toApiResponse())
            }
        } catch (exception: HttpClientErrorException) {
            logger.error(exception.message) // GitHub Api resources limit exceeded, cannot be calculated
        }
    }
}