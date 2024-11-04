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
            dataContainer.sessionId = sessionId
            dataContainer.language = language
            loadRepositories(language)
        }

        try {
            if (repositoriesShouldBeLoaded(availableRequests)) {
                loadRepositories(language)
                availableRequests--;
            }
            for (i in 0..<availableRequests) {
                val classesResponse: ClassesResponse = dataService.getClassesFromRepository(
                    repository = dataContainer.getRepositoryToAnalyse(),
                    language = language,
                )

                dataContainer.repositoriesAnalysed++
                dataContainer.classesAnalysed += classesResponse.classes.size

                analysisService.analyse(classesResponse.classes, dataContainer)
                notificationService.update(sessionId, dataContainer.toApiResponse())
            }
        } catch (exception: HttpClientErrorException) {
            logger.error(exception.message) // GitHub Api resources limit exceeded
        }
    }

    fun repositoriesShouldBeLoaded(availableRequests: Int): Boolean {
        return 100 - (dataContainer.repositoriesAnalysed % 100) < availableRequests
    }

    fun loadRepositories(language: SupportedLanguage) {
        val repositoriesResponse: RepositoriesResponse = dataService.getPopularRepositories(
            language,
            dataContainer.repositories.size / 100)
        dataContainer.repositories.addAll(repositoriesResponse.repositories)
        dataContainer.totalRepositoriesCount = repositoriesResponse.publicRepositoriesCount
    }
}