package week4.day22

import util.repeat
import util.withInput

val SAMPLE = false
//val REPETITIONS = 9
val REPETITIONS = 2000


fun main () {
    println("day$DAY, part2 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val secrets = input.split ("\n").map { Secret (it.toLong ()) }
        val gens = Generators ()

        for (secret in secrets) {
            val gen = Generator (secret)
            if (SAMPLE) {
                println (secret)
            }
            REPETITIONS.repeat {
                gen.next ()
            }
            gens.addGenerator (gen)
        }

        val (run, price) = gens.bestPrice
        println ("Found $price at $run")
    }

    return
}

data class Run (val list: List<Int>) {
    init {
        if (list.size != 4) {
            throw IllegalStateException ()
        }
    }
}

// EOF