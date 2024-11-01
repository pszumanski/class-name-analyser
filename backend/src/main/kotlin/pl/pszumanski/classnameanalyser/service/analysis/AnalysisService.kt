package pl.pszumanski.classnameanalyser.service.analysis

import pl.pszumanski.classnameanalyser.dto.analysis.AnalysisResult
import pl.pszumanski.classnameanalyser.dto.file.GithubFile

interface AnalysisService {

    /**
     * Modifies map to reflect occurrences of class names
     *
     * @param classes list of names to analyse
     * @param words map to update
     * @return summary of analysis
     */
    fun analyse(classes: List<GithubFile>, words: MutableMap<String, Int>): AnalysisResult
}