package pl.pszumanski.classnameanalyser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClassNameAnalyserApplication

fun main(args: Array<String>) {
    runApplication<ClassNameAnalyserApplication>(*args)
}
