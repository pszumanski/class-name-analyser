package pl.pszumanski.classnameanalyser.service

import org.springframework.stereotype.Service
import pl.pszumanski.classnameanalyser.dto.ClassData

@Service
class ClassDataServiceImpl : ClassDataService {
    override fun getData(): ClassData {


        return ClassData("test")
    }
}