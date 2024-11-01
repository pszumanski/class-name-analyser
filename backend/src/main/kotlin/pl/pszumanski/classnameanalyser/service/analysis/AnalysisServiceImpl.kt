package pl.pszumanski.classnameanalyser.service.analysis

import org.springframework.stereotype.Service
import pl.pszumanski.classnameanalyser.dto.analysis.AnalysisResult
import pl.pszumanski.classnameanalyser.dto.file.GithubFile
import pl.pszumanski.classnameanalyser.util.isUpperCamelCase
import pl.pszumanski.classnameanalyser.util.toUniqueWords

@Service
class AnalysisServiceImpl : AnalysisService {

    override fun analyse(classes: List<GithubFile>, words: MutableMap<String, Int>): AnalysisResult {
        var validClasses: Int = 0
        var wordsAnalysed: Int = 0

        classes.filter { it.name.isUpperCamelCase() }
            .forEach {
                val clazzWords: Set<String> = it.name.toUniqueWords()
                validClasses++
                wordsAnalysed += clazzWords.size
                updateWordCount(clazzWords, words)
            }

        return AnalysisResult(validClasses, wordsAnalysed)
    }

    private fun updateWordCount(clazzWords: Set<String>, words: MutableMap<String, Int>) {
        for (clazzWord in clazzWords) {
            words.putIfAbsent(clazzWord, 0)
            words.compute(clazzWord) {_, value -> value?.plus(1) }
        }
    }
}