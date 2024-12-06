package day6

import util.withInput

fun main () {
    withInput (DAY, SAMPLE) { input ->
        val lab = Lab.parse (input)
        lab.patrol ()

        var loops = 0
        lab.visit { point, thing ->
            if (thing == Thing.VISITED) {
                val copy = Lab.parse (input)
                copy.obstruct (point)
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