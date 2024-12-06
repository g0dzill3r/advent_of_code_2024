package day6

import util.withInput

fun main () {
    withInput (DAY, SAMPLE) { input ->
        println (input)
        val lab = Lab.parse (input)
        println (lab)
    }
    return
}

// EOF