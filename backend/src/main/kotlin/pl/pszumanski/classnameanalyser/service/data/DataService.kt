package pl.pszumanski.classnameanalyser.service.data

import pl.pszumanski.classnameanalyser.dto.file.ClassesResponse
import pl.pszumanski.classnameanalyser.dto.repo.GithubRepository
import pl.pszumanski.classnameanalyser.dto.repo.RepositoriesResponse
import pl.pszumanski.classnameanalyser.values.SupportedLanguage

interface DataService {

    fun getClassesFromRepository(
        repository: GithubRepository,
        language: SupportedLanguage,
    ): ClassesResponse

    fun getPopularRepositories(
        language: SupportedLanguage,
        page: Int,
    ): RepositoriesResponse
}