package pl.pszumanski.classnameanalyser.dto.file

import com.fasterxml.jackson.annotation.JsonProperty

data class ClassesResponse(@JsonProperty("total_count") val totalCount: Int, val items: List<GithubFile>) {
}