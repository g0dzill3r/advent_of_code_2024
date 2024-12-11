package day11

import util.repeat
import util.withInput
import java.math.BigInteger

fun main () {
    println("day$DAY, part2")
    withInput(DAY, SAMPLE) { input ->
        val model = ModelTwo (input)
        println ("START: ${model.stones}")
        for (i in 1 .. 75) {
            model.tick ()
            println (i)
        }
        var total = BigInteger.ZERO
        model.stones.forEach { (value, count) ->
            total += count
        }
        println (total)
    }
    return
}

// EOF