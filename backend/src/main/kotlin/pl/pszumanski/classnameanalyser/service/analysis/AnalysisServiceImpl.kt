package pl.pszumanski.classnameanalyser.service.analysis

import org.springframework.stereotype.Service
import pl.pszumanski.classnameanalyser.dto.file.GithubFile
import pl.pszumanski.classnameanalyser.util.isUpperCamelCase
import pl.pszumanski.classnameanalyser.util.toUniqueWords
import pl.pszumanski.classnameanalyser.websocket.DataContainer

@Service
class AnalysisServiceImpl : AnalysisService {

    override fun analyse(classes: List<GithubFile>, dataContainer: DataContainer) {
        classes.filter { it.name.isUpperCamelCase() }
            .forEach {
                val clazzWords: Set<String> = it.name.toUniqueWords()
                dataContainer.validClasses++
                dataContainer.wordsAnalysed += clazzWords.size
                updateWordCount(clazzWords, dataContainer.words)
            }
    }

    private fun updateWordCount(clazzWords: Set<String>, words: MutableMap<String, Int>) {
        for (clazzWord in clazzWords) {
            words.putIfAbsent(clazzWord, 0)
            words.compute(clazzWord) { _, value -> value?.plus(1) }
        }
    }
}