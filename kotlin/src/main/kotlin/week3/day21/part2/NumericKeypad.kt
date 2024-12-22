package week3.day21.part2

import week3.day21.part1.DirectionalKeypad
import week3.day21.part1.NumericKeypad
import week3.day21.part1.Position

/**
 * A utility class for precalculating the cost of all the possible transitions
 */

class NumericUtil {
    var cur = NumericKeypad.Button.ACTIVATE

    fun press (symbols: String): String {
        return buildString {
            for (symbol in symbols) {
                val next = NumericKeypad.Button.fromSymbol (symbol)
                val seq = cost[cur to next] !!
                append (seq)
                cur = next
            }
        }
    }

    companion object {
        val cost: Map<Pair<NumericKeypad.Button, NumericKeypad.Button>, String> = buildMap {
            val kpad = NumericKeypad ()
            val valid = NumericKeypad.Button.entries.filter { it != NumericKeypad.Button.MISSING }
            for (b1 in valid) {
                for (b2 in valid) {
                    val paths = kpad.traverse (b1, b2)
                    val path = buildString {
                        paths[0].forEach {
                            append(it.symbol)
                        }
                        append ('A')
                    }
                    put (b1 to b2, path)
                }
            }
        }
    }
}

fun main () {
    val code = "029A"
    val npad = NumericUtil ()
    println (npad.press (code))

}

// EOF