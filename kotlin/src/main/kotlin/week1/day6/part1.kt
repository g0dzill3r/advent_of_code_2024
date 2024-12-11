package week1.day6

import util.withInput

val DAY = 6
val SAMPLE = false

fun main () {
    withInput (DAY, SAMPLE) { input ->
        val lab = Lab.parse (input)
        lab.patrol ()
        println (lab.visitCount)
    }
    return
}

// EOF