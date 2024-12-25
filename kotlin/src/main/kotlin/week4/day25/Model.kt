package week4.day25

object Type {
    val LOCK = '#'
    val KEY = '.'
}

abstract class Thing (val size: Int, val pins: List<Int>) {
    fun overlaps (other: Thing): Boolean {
        for (i in 0 until pins.size) {
            if (pins[i] + other.pins[i] >= size) {
                return true
            }
        }
        return false
    }
}
class Lock (size: Int, pins: List<Int>): Thing (size, pins)
class Key (size: Int, pins: List<Int>): Thing (size, pins)

class Model (val locks: List<Lock>, val keys: List<Key>) {

    companion object {
        fun parse (input: String): Model {
            val iter = input.split ("\n\n")
            val things = iter.map { parse (it.split ("\n")) }
            return Model (things.filter { it is Lock }.map { it as Lock }, things.filter { it is Key }.map { it as Key })
        }

        fun parse (input: List<String>) : Thing {
            val pins = input[0].length
            val first = input[0][0]
            val els = buildList {
                for (i in 0 until pins) {
                    outer@for (j in 1 until input.size) {
                        if (input[j][i] != first) {
                            add (j - 1)
                            break@outer
                        }
                    }
                }
            }
            return if (first == Type.LOCK) {
                Lock (input.size - 1, els)
            } else {
                Key (input.size - 1, els.map { pins - it })
            }
        }
    }
}



// EOF