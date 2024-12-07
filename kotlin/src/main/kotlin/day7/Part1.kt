package day7

import util.withInput

val DAY = 7
val SAMPLE = false

fun main () {
    withInput (DAY, SAMPLE) { input ->
        val equations = Equations.parse (input)

        var total = 0.toBigInteger ()
        equations.equations.forEach {
            if (it.isPossible()) {
                total += it.output
            }
        }
        println (total)
    }
    return
}
