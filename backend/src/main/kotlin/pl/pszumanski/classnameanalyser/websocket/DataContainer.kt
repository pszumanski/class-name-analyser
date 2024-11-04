package pl.pszumanski.classnameanalyser.websocket

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import pl.pszumanski.classnameanalyser.dto.api.ApiResponse
import pl.pszumanski.classnameanalyser.dto.api.Word
import pl.pszumanski.classnameanalyser.dto.repo.GithubRepository
import pl.pszumanski.classnameanalyser.values.SupportedLanguage
import kotlin.math.max

@Component
@Scope(scopeName = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
class DataContainer {
    var sessionId: String = ""
    var repositories: MutableList<GithubRepository> = ArrayList()
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
        words = words.map { Word(it.key, it.value) },
        averageWordsPerClass = wordsAnalysed.toDouble() / max(1, validClasses),
        percentageOfValidClasses = (validClasses.toDouble() / max(1, classesAnalysed)) * 100,
    )

    fun getRepositoryToAnalyse(): GithubRepository = repositories[repositoriesAnalysed]

    fun isEmpty(): Boolean = repositories.isEmpty()
}