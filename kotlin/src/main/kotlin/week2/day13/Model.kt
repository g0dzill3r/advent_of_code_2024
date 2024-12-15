package week2.day13

import java.util.regex.Pattern
import kotlin.math.max

open class Coordinates (val x: Long, val y: Long) {
    override fun toString(): String = "X=$x, Y=$y"
    fun offset (offset: Long): Coordinates {
        return Coordinates (x + offset, y + offset)
    }
}
class DeltaCoordinates (x: Long, y: Long) : Coordinates (x, y) {
    override fun toString(): String = "X+$x, Y+$y"
}



data class Machine (val a: DeltaCoordinates, val b: DeltaCoordinates, var prize: Coordinates) {
    data class Possibility (val a: Long, val b: Long, var cost: Long)

    val A_COST = 3
    val B_COST = 1

    fun cost (a: Long, b: Long): Long {
        return a * A_COST + b * B_COST
    }

    fun offset (offset: Long) {
        prize = prize.offset (offset)
        return
    }

    fun possibilities (): List<Possibility> {
        // Figure out the maximum number of times that we could press A
        // and cycle through all possibiltiies

        return buildList {
            val xmax = max (prize.x / a.x, prize.x / b.x)

            for (apress in 0 .. xmax) {

                // See if the remainder could be satisfied with b presses

                val xrem = prize.x - apress * a.x
                if (xrem % b.x != 0L) {
                    continue
                }
                val bpress = xrem / b.x

                // See if this configuration of presses satisfies the Y prize coordinate

                if (apress * a.y + bpress * b.y == prize.y) {
                    add (Possibility (apress, bpress, cost (apress, bpress)))
                }
            }
        }
    }

    companion object {
        private val pattern = Pattern.compile (
            "Button A: X\\+(\\d+), Y\\+(\\d+) " +
                    "Button B: X\\+(\\d+), Y\\+(\\d+) " +
                    "Prize: X=(\\d+), Y=(\\d+)")
        fun parse (str: String): Machine {
            val matcher = pattern.matcher (str)
            if (! matcher.matches ()) {
                throw Exception ("Unrecognized input: $str")
            }
            var i = 1
            fun getPairs (): Pair<Long, Long> {
                return matcher.group (i ++).toLong () to matcher.group (i ++).toLong ()
            }
            val (ax, ay) = getPairs ()
            val (bx, by) = getPairs ()
            val (px, py) = getPairs ()
            return Machine (
                DeltaCoordinates (ax, ay),
                DeltaCoordinates (bx, by),
                Coordinates (px, py)
            )
        }
    }
}

class Arcade (val input: String, val offset: Long = 0L) {
    val machines = parse (input, offset)

    override fun toString (): String {
        return buildString {
            machines.forEach {
                append (it.toString ())
                append ("\n")
            }
        }
    }

    companion object {
        fun parse (input: String, offset: Long): List<Machine> {
            val iter = input.split ("\n").filter { it.isNotEmpty() }.iterator ()
            return buildList {
                while (iter.hasNext()) {
                    val str = iter.next() + " " + iter.next() + " " + iter.next()
                    val machine = Machine.parse (str)
                    machine.offset (offset)
                    add (machine)
                }
            }
        }
    }
}

// EOF

