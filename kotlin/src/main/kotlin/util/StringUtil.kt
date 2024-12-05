package util

object StringUtil {
    fun prefixToWidth (str: String, prefix: Char, width: Int): String {
        val missing = width - str.length
        return "$prefix".repeat (missing) + str
    }
}

// EOF