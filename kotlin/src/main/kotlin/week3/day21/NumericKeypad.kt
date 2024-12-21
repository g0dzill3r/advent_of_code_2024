package week3.day21

import util.interactive

/**
 * Models the keypad with the numeric buttons.
 *
 *   +---+---+---+
 *   | 7 | 8 | 9 |
 *   +---+---+---+
 *   | 4 | 5 | 6 |
 *   +---+---+---+
 *   | 1 | 2 | 3 |
 *   +---+---+---+
 *       | 0 | A |
 *       +---+---+
 */

class NumericKeypad {
    var cur = Button.ACTIVATE
    var accum: MutableList<List<List<Direction>>> = mutableListOf ()

    fun reset () {
        cur = Button.ACTIVATE
        accum = mutableListOf ()
        return
    }

    fun press (seq: String): List<List<Direction>> {
        val els = buildList {
            seq.forEach {
                val press = press (Button.fromSymbol (it))
                addAll (press)
                accum.add (press)
            }
        }
        return els
    }

    fun press (button: Button): List<List<Direction>> {
        val paths = traverse (cur, button)
        cur = button
        return paths.map {
            buildList {
                addAll (it)
                add (Direction.ACTIVATE)
            }
        }
    }

    fun traverse (from: Button, to: Button): List<List<Direction>> {
        val paths = Position.traversals (from.pos, to.pos, Button.MISSING.pos)
        return paths.map { Position.toDirections (it) }
    }

    enum class Button (val symbol: Char, val pos: Position) {
        SEVEN ('7', Position(0, 0)),
        EIGHT ('8', Position(0, 1)),
        NINE ('9', Position (0, 2)),
        FOUR ('4', Position(1, 0)),
        FIVE ('5', Position(1, 1)),
        SIX ('6', Position(1, 2)),
        ONE ('1', Position(2, 0)),
        TWO ('2', Position(2, 1)),
        THREE ('3', Position(2, 2)),
        MISSING ('*', Position (3, 0)),
        ZERO ('0', Position (3, 1)),
        ACTIVATE ('A', Position(3, 2));

        override fun toString () = "N$symbol"

        companion object {
            private val map = entries.associateBy { it.symbol }
            fun fromSymbol (symbol: Char) = map[symbol]!!
        }
    }
}

fun main () {
    println ("""
        7 8 9 
        4 5 6
        1 2 3 
          0 A
    """.trimIndent())
    val keypad = NumericKeypad ()
    println ("CURRENT: ${keypad.cur}")
    cli ("kpad> ") { args ->
        if (args.size != 1) {
            println ("USAGE: <seq>")
        } else {
            val seq = args[0]
            keypad.press (seq)
            val combos = combinations (keypad.accum)
            println (combos)
            val flattened = combos.map { it.flatten }
            flattened.forEach { seq ->
                println (seq.encoded)
            }
        }
        println ("CURRENT: ${keypad.cur}")
        false
    }
}

// EOF