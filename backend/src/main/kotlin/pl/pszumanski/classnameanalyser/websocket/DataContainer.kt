package pl.pszumanski.classnameanalyser.websocket

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import pl.pszumanski.classnameanalyser.dto.api.ApiResponse
import pl.pszumanski.classnameanalyser.dto.repo.GithubRepository
import pl.pszumanski.classnameanalyser.values.SupportedLanguage
import java.util.*
import kotlin.collections.HashMap

@Component
@Scope(scopeName = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
class DataContainer {
    var sessionId: String = ""
    var repositories: List<GithubRepository> = ArrayList()
    var language: SupportedLanguage = SupportedLanguage.JAVA
    var totalRepositoriesCount: Int = 0
    var repositoriesAnalysed: Int = 0
    var classesAnalysed: Int = 0
    var validClasses: Int = 0
    var wordsAnalysed: Int = 0
    val words: MutableMap<String, Int> = HashMap()

    fun toApiResponse(): ApiResponse = ApiResponse(
        language = language,
        totalRepositoriesCount = totalRepositoriesCount,
        repositoriesAnalysed = repositoriesAnalysed,
        classesAnalysed = classesAnalysed,
        validClasses = validClasses,
        wordsAnalysed = wordsAnalysed,
        words = words,
        averageWordsPerClass = (wordsAnalysed.toDouble()) / validClasses,
        percentageOfValidClasses = (validClasses.toDouble() / classesAnalysed) * 100,
    )

    fun getRepositoryToAnalyse(): GithubRepository = repositories[repositoriesAnalysed % repositories.size]

    fun getPageToAnalyse(): Int = repositoriesAnalysed / repositories.size

    fun isEmpty(): Boolean = repositories.isEmpty()
}