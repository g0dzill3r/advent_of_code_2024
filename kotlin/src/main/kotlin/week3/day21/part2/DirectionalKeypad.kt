package week3.day21.part2

import util.repeat
import week3.day21.part1.DirectionalKeypad

class DirectionalUtil {
    var cur = DirectionalKeypad.Button.ACTIVATE

    fun press (symbols: String): String {
        return buildString {
            for (symbol in symbols) {
                val next = DirectionalKeypad.Button.fromSymbol (symbol)
                val seq = cost[cur to next]
                if (seq == null) {
                    throw IllegalStateException ("Missing cost for $cur -> $next")
                }
                append (seq)
                cur = next
            }
        }
    }

    companion object {
        val cost: Map<Pair<DirectionalKeypad.Button, DirectionalKeypad.Button>, String> = buildMap {
            val dpad = DirectionalKeypad ()
            val valid = DirectionalKeypad.Button.entries.filter { it != DirectionalKeypad.Button.MISSING }
            for (b1 in valid) {
                for (b2 in valid) {
                    val paths = dpad.traverse (b1, b2)
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
    val npad = NumericUtil()
    val dpad = DirectionalUtil()

    var code = "029A"
    code = npad.press(code)
    25.repeat {
        code = dpad.press(code)
    }

    println(code.length)
}


// EOF