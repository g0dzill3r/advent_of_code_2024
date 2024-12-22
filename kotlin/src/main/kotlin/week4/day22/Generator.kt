package week4.day22

import util.repeat

class Generators {
    val gens = mutableListOf<Generator> ()
    fun addGenerator (gen: Generator) {
        gens.add (gen)
        return
    }

    fun getPrices (run: Run): List<Pair<Secret, Int?>> {
        return buildList {
            gens.forEach { gen ->
                add (gen.secret to gen.runs[run])
            }
        }
    }

    val bestPrice: Pair<Run, Int>
        get () {
            val runs = buildSet {
                gens.forEach { gen ->
                    gen.runs.keys.forEach { run ->
                        add(run)
                    }
                }
            }
            var maxPrice = 0
            var maxRun: Run? = null
            runs.forEach { run ->
                val price = getPrice (run)
                if (price > maxPrice) {
                    maxPrice = price
                    maxRun = run
                }
            }
            return maxRun!! to maxPrice
        }

    fun getPrice (run: Run): Int {
        var total = 0
        gens.forEach { gen ->
            val value = gen.runs[run]
            if (value != null) {
                total += value
            }
        }
        return total
    }
}

class Generator (val secret: Secret) {
    var cur = secret
    val prices = mutableListOf<Int> ()
    val deltas = mutableListOf<Int> ()
    val runs = mutableMapOf<Run, Int> ()

    init {
        addPrice ()
    }

    fun next () {
        cur = cur.next
        addPrice ()
        addDelta ()
        if (deltas.size == 4) {
            val run = Run (deltas.toList ())
            if (! runs.containsKey (run)) {
                runs[run] = cur.price
            }
        }
        return
    }


    private fun addDelta () {
        if (deltas.size == 4) {
            deltas.removeAt (0)
        }
        val size = prices.size
        if (size > 1) {
            deltas.add (prices[size - 1] - prices[size - 2])
        }
        return
    }

    private fun addPrice () {
        if (prices.size == 4) {
            prices.removeAt (0)
        }
        prices.add (cur.price)
        return
    }

    fun dump () {
        println ("secret=$secret\n - cur=$cur\n - prices=$prices\n - deltas=$deltas")
    }
}

fun main () {
    val secret = Secret (123)
    val gen = Generator (secret)
    9.repeat {
        gen.next ()
    }
    println (gen.runs)
    return
}