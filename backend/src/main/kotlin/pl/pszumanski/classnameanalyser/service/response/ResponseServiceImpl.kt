package pl.pszumanski.classnameanalyser.service.response

import org.springframework.stereotype.Service
import pl.pszumanski.classnameanalyser.dto.ApiResponse
import pl.pszumanski.classnameanalyser.dto.file.ClassesResponse
import pl.pszumanski.classnameanalyser.dto.repo.GithubRepository
import pl.pszumanski.classnameanalyser.dto.repo.RepositoriesResponse
import pl.pszumanski.classnameanalyser.service.analysis.AnalysisService
import pl.pszumanski.classnameanalyser.service.data.DataService
import pl.pszumanski.classnameanalyser.values.SupportedLanguage

@Service
class ResponseServiceImpl(val dataService: DataService, val analysisService: AnalysisService) : ResponseService {

    override fun getResponse(language: SupportedLanguage): ApiResponse {
        val repositoriesResponse: RepositoriesResponse = dataService.getPopularRepositories(language)
        val repositoriesCount: Int = repositoriesResponse.repositories.size
        val popularRepositories: List<GithubRepository> = repositoriesResponse.repositories

        var repositoriesAnalysed = 0
        var classesAnalysed = 0
        var validClasses = 0
        var wordsAnalysed = 0
        val words: MutableMap<String, Int> = HashMap()

        val requestsLimit = dataService.getRemainingRequests()

        for (i in 0..< requestsLimit) {
            val classesResponse: ClassesResponse = dataService.getClassesFromRepository(
                repository = popularRepositories[repositoriesAnalysed % repositoriesCount],
                language = language,
                page = repositoriesAnalysed / repositoriesCount
            )

            repositoriesAnalysed++
            classesAnalysed += classesResponse.items.size

            val analysisResult = analysisService.analyse(classesResponse.items, words)
            validClasses += analysisResult.validClasses
            wordsAnalysed += analysisResult.wordsAnalysed
        }

        return ApiResponse(
            language = language,
            totalRepositoriesCount = repositoriesResponse.publicRepositoriesCount,
            repositoriesAnalysed = repositoriesAnalysed,
            classesAnalysed = classesAnalysed,
            validClasses = validClasses,
            wordsAnalysed = wordsAnalysed,
            averageWordsPerClass = (wordsAnalysed.toDouble()) / validClasses,
            percentageOfValidClasses = (validClasses.toDouble() / classesAnalysed) * 100,
            words = words,
        )
    }
}