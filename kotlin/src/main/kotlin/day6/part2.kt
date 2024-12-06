package day6

import util.withInput

fun main () {
    withInput (DAY, SAMPLE) { input ->
        val lab = Lab.parse (input)
        var loops = 0
        lab.visit { row, col, thing ->
            if (thing == Thing.EMPTY) {
                val copy = Lab.parse (input)
                copy.update (row, col, Thing.OBSTRUCTION)
                try {
                    copy.patrol ()
                } catch (e: Exception) {
                    loops ++
                }
            }
        }
        println (loops)
    }
    return
}

// EOF