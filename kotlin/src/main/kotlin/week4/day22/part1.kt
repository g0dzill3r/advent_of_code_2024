package week4.day22

import util.withInput

val DAY = 22

fun main () {
    println("day$DAY, part1 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val seq = input.split ("\n").map { Secret (it.toLong ()) }
        var total = 0L
        seq.forEach {
            val res = it.after (2000)
            total += res.value
            println ("$it: $res")
        }
        println (total)
    }
    return
}

// EOF