package week1.day7

import util.withInput

fun main () {
    withInput(DAY, SAMPLE) { input ->
        val equations = Equations.parse(input)

        var total = 0.toBigInteger ()
        equations.equations.forEach {
            if (it.isPossible2()) {
                total += it.output
            }
        }
        println (total)
    }
}

// EOF