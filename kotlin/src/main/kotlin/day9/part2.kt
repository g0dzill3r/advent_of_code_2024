package day9

import util.withInput


fun main () {
    println ("day$DAY, part2")
    withInput(DAY, SAMPLE) { input ->
        val model = Model (input)
        model.compact2 ()
        println (model.checksum ())
    }
    return
}

// EOF