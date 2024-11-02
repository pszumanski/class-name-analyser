package pl.pszumanski.classnameanalyser.service.response

import org.springframework.stereotype.Service
import pl.pszumanski.classnameanalyser.dto.api.ApiRequest
import pl.pszumanski.classnameanalyser.dto.file.ClassesResponse
import pl.pszumanski.classnameanalyser.dto.repo.RepositoriesResponse
import pl.pszumanski.classnameanalyser.service.analysis.AnalysisService
import pl.pszumanski.classnameanalyser.service.data.DataService
import pl.pszumanski.classnameanalyser.values.SupportedLanguage
import pl.pszumanski.classnameanalyser.websocket.DataContainer
import pl.pszumanski.classnameanalyser.websocket.NotificationService

@Service
class ResponseServiceImpl(val dataService: DataService, val analysisService: AnalysisService,
                          val dataContainer: DataContainer, val notificationService: NotificationService)
    : ResponseService {

    override fun getResponse(apiRequest: ApiRequest) {
        val language = SupportedLanguage.fromString(apiRequest.language)
        val sessionId: String = apiRequest.sessionId

        if (dataContainer.isEmpty()) {
            val repositoriesResponse: RepositoriesResponse = dataService.getPopularRepositories(language)

            dataContainer.sessionId = sessionId
            dataContainer.language = language
            dataContainer.repositories = repositoriesResponse.repositories
            dataContainer.totalRepositoriesCount = repositoriesResponse.publicRepositoriesCount
        }

        val requestsLimit = dataService.getRemainingRequests()

        for (i in 0..< requestsLimit) {
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
    }
}