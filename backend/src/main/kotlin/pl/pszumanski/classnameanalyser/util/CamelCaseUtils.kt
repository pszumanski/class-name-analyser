package pl.pszumanski.classnameanalyser.util

fun String.isUpperCamelCase(): Boolean {
    var lastCharWasUpper: Boolean = false

    if (this[0].isLowerCase()) return false

    for (c: Char in this.toCharArray()) {
        if (c == '.') break
        if (!c.isLetter()) return false
        if (c.isUpperCase() && lastCharWasUpper) return false
        lastCharWasUpper = c.isUpperCase()
    }
    return true
}

fun String.toUniqueWords(): Set<String> {
    val words: MutableSet<String> = HashSet()
    val sb: StringBuilder = StringBuilder()

    for (c: Char in this.toCharArray()) {
        if (c == '.') break
        if (c.isUpperCase()) {
            if (sb.isNotEmpty()) {
                words.add(sb.toString())
            }
            sb.clear()
        }
        sb.append(c)
    }

    if (sb.isNotEmpty()) {
        words.add(sb.toString())
    }

    return words
}