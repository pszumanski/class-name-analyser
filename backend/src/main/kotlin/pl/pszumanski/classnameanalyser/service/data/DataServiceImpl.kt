package pl.pszumanski.classnameanalyser.service.data

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import pl.pszumanski.classnameanalyser.dto.file.ClassesResponse
import pl.pszumanski.classnameanalyser.dto.repo.GithubRepository
import pl.pszumanski.classnameanalyser.dto.repo.RepositoriesResponse
import pl.pszumanski.classnameanalyser.values.SupportedLanguage

@Service
class DataServiceImpl(val githubClient: RestClient) : DataService {

    val resultsPerPage: String = "per_page=100"

    override fun getClassesFromRepository(
        repository: GithubRepository,
        language: SupportedLanguage,
        page: Int,
    ): ClassesResponse = githubClient
        .get()
        .uri("/search/code?q=extension:${language.extension} repo:${repository.name}&$resultsPerPage&page=$page") //
        .retrieve()
        .body<ClassesResponse>() ?: throw IllegalStateException("Fetching error")

    override fun getPopularRepositories(language: SupportedLanguage): RepositoriesResponse = githubClient.get()
        .uri("/search/repositories?q=language:${language.name.lowercase()}&$resultsPerPage&sort=stars")
        .retrieve()
        .body<RepositoriesResponse>() ?: throw IllegalStateException("Fetching error")
}