package week3.day21

/**
 * Implements the directional keypad.
 *
 *      +---+---+
 *      | ^ | A |
 *  +---+---+---+
 *  | < | v | > |
 *  +---+---+---+
 *
 */

class DirectionalKeypad {
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
        MISSING ('*', Position (0, 0)),
        UP ('^', Position(0, 1)),
        ACTIVATE ('A', Position(0, 2)),
        LEFT ('<', Position (1, 0)),
        DOWN ('v', Position(1, 1)),
        RIGHT ('>', Position(1, 2));

        override fun toString () = "D$symbol"

        companion object {
            private val map = entries.associateBy { it.symbol }
            fun fromSymbol (symbol: Char) = map[symbol]!!
        }
    }
}

fun main () {
    val layout = """
          ^ A
        < v >
    """.trimIndent()
    println (layout)

    val dpad = DirectionalKeypad ()
    println ("CURRENT: ${dpad.cur}")

    cli ("dpad> ") { args ->
        if (args.size > 0) {
            when (args[0]) {
                "?" -> {
                    println ("accum=${dpad.accum}")
                }
                "b" -> println (layout)
                "p" -> {
                    if (args.size != 2) {
                        println ("USAGE: ${args[0]} <button>")
                    } else {
                        val paths = dpad.press (args [1])
                        println (paths)
                    }
                }
                "t" -> {
                    if (args.size != 3) {
                        println ("USAGE: ${args[0]} <from> <to>")
                    } else {
                        val from = DirectionalKeypad.Button.fromSymbol(args[1][0])
                        val to = DirectionalKeypad.Button.fromSymbol (args[2][0])
                        println ("TRAVERSE $from to $to")
                        val paths = dpad.traverse (from, to)
                        println (paths)
                    }
                }
                else -> println("ERROR: Unrecognized command: $args[0]")
            }
        }
        println ("cur=${dpad.cur}")
//            val seq = args[0]
//            dpad.press (seq)
//            val combos = combinations (dpad.accum)
//            println (combos)
//            val flattened = combos.map { it.flatten }
//            flattened.forEach { seq ->
//                println (seq.encoded)
//            }
        false
    }
}

// EOF