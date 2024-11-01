package pl.pszumanski.classnameanalyser.service

import pl.pszumanski.classnameanalyser.dto.ClassData

interface ClassDataService {
    fun getData(): ClassData
}