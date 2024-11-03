package pl.pszumanski.classnameanalyser.service.data

import pl.pszumanski.classnameanalyser.dto.file.ClassesResponse
import pl.pszumanski.classnameanalyser.dto.repo.GithubRepository
import pl.pszumanski.classnameanalyser.dto.repo.RepositoriesResponse
import pl.pszumanski.classnameanalyser.values.SupportedLanguage

interface DataService {

    fun getPopularRepositories(language: SupportedLanguage): RepositoriesResponse

    fun getClassesFromRepository(
        repository: GithubRepository,
        language: SupportedLanguage,
        page: Int,
    ): ClassesResponse
}