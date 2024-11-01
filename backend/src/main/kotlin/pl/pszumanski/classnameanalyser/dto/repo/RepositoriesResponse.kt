package pl.pszumanski.classnameanalyser.dto.repo

import com.fasterxml.jackson.annotation.JsonProperty

data class RepositoriesResponse(@JsonProperty("total_count") val publicRepositoriesCount: Int, @JsonProperty("items")
val repositories:
List<GithubRepository>) {
}