package pl.pszumanski.classnameanalyser.dto

import pl.pszumanski.classnameanalyser.values.SupportedLanguage

data class ApiResponse(val language: SupportedLanguage, val totalRepositoriesCount: Int, val repositoriesAnalysed: Int, val classesAnalysed: Int,
                       val validClasses: Int, val wordsAnalysed: Int, val averageWordsPerClass: Double,
                       val percentageOfValidClasses: Double, val words: Map<String, Int>)
