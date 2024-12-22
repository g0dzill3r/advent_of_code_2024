package week3.day19

import util.interactive

fun possible (available: List<String>, desired: String): Long {
    return available.sumOf {
        if (desired == it) {
            1L
        } else if (desired.startsWith (it)) {
            val substring = desired.substring (it.length, desired.length)
            possibleMemoized (available, substring)
        } else {
            0L
        }
    }
}

private val cache = mutableMapOf<String, Long>()

fun possibleMemoized (available: List<String>, desired: String): Long {
    return cache.getOrPut (desired) {
        possible(available, desired)
    }
}

fun main () {
    val available = listOf ("r", "wr", "b", "g", "bwu", "rb", "gbbr")
    println ("available: $available")
    interactive ("?> ") {
        println (possible (available, it))
        false
    }
    return
}

// EOF