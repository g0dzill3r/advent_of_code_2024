package util

import java.util.regex.Pattern

fun interactive (prompt: String = "> ", func: (String) -> Boolean) {
    do {
        print (prompt)
        val str = readLine () as String
        try {
            if (func (str)) {
                break
            }
        } catch (t: Throwable) {
            println ("ERROR: ${t.message}")
            t.printStackTrace()
        }
    } while (true)
}

fun main () {
    interactive ("?> ") { input ->
        val args = input.split (Pattern.compile ("\\s+")).filter { it.isNotBlank() }
        println (args)
        false
    }
}

// EOF