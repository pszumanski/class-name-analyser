package pl.pszumanski.classnameanalyser.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.pszumanski.classnameanalyser.dto.ClassData
import pl.pszumanski.classnameanalyser.service.ClassDataService

@RestController
@RequestMapping("/public/api/v1/data")
class ClassDataController(val classDataService: ClassDataService) {

    @GetMapping
    fun getClassData(): ClassData = classDataService.getData()
}