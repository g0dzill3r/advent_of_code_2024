package week3.day19

import util.withInput

val DAY = 19
val SAMPLE = false

fun main () {
    println("day$DAY, part1 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val model = Model.parse (input)
        val regex = buildString {
            append ("(")
            model.available.forEachIndexed { i, it ->
                if (i != 0) {
                    append ("|")
                }
                append (it)
            }
            append (")+")
        }
        val pattern = java.util.regex.Pattern.compile (regex)

        var total = 0
        model.desired.forEachIndexed { i, it ->
            if (pattern.matcher (it.seq).matches ()) {
                total ++
            }
        }
        println (total)
    }
    return
}

// EOF