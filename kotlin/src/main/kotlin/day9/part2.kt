package day9

import util.withInput


fun main () {
    println ("day$DAY, part2")
    withInput(DAY, SAMPLE) { input ->
        val model = Model (input)
        println (model.encoded)
        model.compact2 ()
        println (model.checksum ())
    }
    return
}

// EOF