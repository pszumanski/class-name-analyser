package pl.pszumanski.classnameanalyser.service.analysis

import pl.pszumanski.classnameanalyser.dto.file.GithubFile
import pl.pszumanski.classnameanalyser.websocket.DataContainer

interface AnalysisService {

    /**
     * Analyse classes and update data container with the results
     *
     * @param classes list of classes to analyse
     * @param dataContainer container to update with the results
     */
    fun analyse(classes: List<GithubFile>, dataContainer: DataContainer)
}