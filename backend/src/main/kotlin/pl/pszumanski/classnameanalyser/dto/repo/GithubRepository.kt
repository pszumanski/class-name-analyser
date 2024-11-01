package pl.pszumanski.classnameanalyser.dto.repo

import com.fasterxml.jackson.annotation.JsonProperty

data class GithubRepository(@JsonProperty("full_name") val name: String) {
}