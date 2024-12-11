package week1.day7

import util.withInput

fun main () {
    // Let's see if there are any that can work multiple ways

    withInput(DAY, SAMPLE) { input ->
        val equations = Equations.parse(input)
        var total = 0
        equations.equations.forEach { eq ->
            val possible = eq.isPossibleWithCount()
            if (possible.size > 1) {
                possible.forEach { (inputs, ops) ->
                    println (eq.formatted(inputs, ops))
                }
                println ()
                total ++
            }
        }
        println ("$total equations")
    }

    println ("\n\n")

    withInput(DAY, SAMPLE) { input ->
        val equations = Equations.parse(input)
        var total = 0
        equations.equations.forEach { eq ->
            val possible = eq.isPossible2WithCount()
            if (possible.size > 1) {
                possible.forEach { (inputs, ops) ->
                    println (eq.formatted(inputs, ops))
                }
                println ()
                total ++
            }
        }
        println ("$total equations")
    }
    return
}

// EOF