package pl.pszumanski.classnameanalyser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class ClassNameAnalyserApplication

fun main(args: Array<String>) {
    runApplication<ClassNameAnalyserApplication>(*args)
}
