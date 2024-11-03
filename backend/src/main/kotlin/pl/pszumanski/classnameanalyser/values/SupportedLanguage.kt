package pl.pszumanski.classnameanalyser.values

enum class SupportedLanguage(val extension: String) {
    JAVA("java"),
    KOTLIN("kt");

    companion object {
        fun fromString(language: String): SupportedLanguage {
            return when (language.lowercase()) {
                "java" -> JAVA
                "kotlin" -> KOTLIN
                else -> JAVA
            }
        }
    }
}