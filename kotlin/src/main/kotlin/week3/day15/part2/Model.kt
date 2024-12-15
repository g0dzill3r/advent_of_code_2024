package week3.day15.part2

import week3.day15.Direction
import week3.day15.Point
import week3.day15.catenate

enum class Thing (val symbol: Char) {
    EMPTY ('.'),
    ROBOT ('@'),
    BOX ('O'),
    BOX_LEFT ('['),
    BOX_RIGHT (']'),
    WALL ('#');

    val isBox: Boolean
        get() = this == BOX_LEFT || this == BOX_RIGHT

    companion object {
        private val fromSymbol = entries.associateBy { it.symbol }
        fun fromSymbol(symbol: Char) = fromSymbol[symbol] as Thing
    }
}

class BlockedException : Exception("Boxes are blocked from moving.")

data class Grid (val rows: Int, val cols: Int, val data: MutableList<Thing>) {
    var robot = fromIndex (data.indexOf (Thing.ROBOT))

    init {
        update (robot, Thing.EMPTY)
        update (robot.add (Direction.RIGHT), Thing.EMPTY)
    }

    fun toIndex (row: Int, col: Int): Int = row * cols + col
    fun toIndex (point: Point): Int = toIndex (point.row, point.col)
    fun fromIndex (index: Int): Point = Point (index / cols, index % cols)
    fun thingAt (point: Point): Thing = data[toIndex (point.row, point.col)]
    fun update (point: Point, thing: Thing) {
        data[toIndex (point)] = thing
        return
    }

    fun move (dir: Direction) {
        val maybe = robot.add (dir)
        when (thingAt (maybe)) {
            Thing.EMPTY -> robot = maybe
            Thing.WALL -> Unit
            else -> {
                moveBlocks (maybe, dir)
            }
        }
    }

    fun moveBlocks (point: Point, dir: Direction) {
        when (dir) {

            // Deal with the left and right cases

            Direction.LEFT, Direction.RIGHT -> {
                var cur = robot
                do {
                    cur = cur.add (dir)
                } while (thingAt (cur).isBox)
                if (thingAt (cur) == Thing.EMPTY) {
                    while (cur != robot) {
                        val next = cur.add (dir.opposite)
                        update (cur, thingAt (next))
                        cur = next
                    }
                    robot = robot.add (dir)
                    update (robot, Thing.EMPTY)
                }
            }

            // And deal with the more complicated up and down cases

            Direction.UP, Direction.DOWN -> {
                try {
                    val maybe = robot.add (dir)
                    findMoves (maybe, dir).reversed ().forEach {
                        val thing = thingAt (it)
                        update (it.add (dir), thing)
                        update (it, Thing.EMPTY)
                    }
                    robot = maybe
                } catch (e: BlockedException) {
                    // IGNORED
                }
            }
        }
        return
    }

    /**
     * Find all the moves that occur my pushing on this point
     */

    fun findMoves (point: Point, dir: Direction, moves: MutableSet<Point> = mutableSetOf ()): Set<Point> {
        if (! thingAt (point).isBox) {
            return moves
        }

        // Find the other point that might move

        val other = if (thingAt (point) == Thing.BOX_RIGHT) {
            point.add (Direction.LEFT)
        } else {
            point.add (Direction.RIGHT)
        }

        // See if this box can move in the specified direction

        val next1 = point.add (dir)
        val next2 = other.add (dir)
        if (thingAt (next1) != Thing.WALL && thingAt (next2) != Thing.WALL) {
            moves.add (point)
            moves.add (other)
            findMoves (next1, dir, moves)
            findMoves (next2, dir, moves)
        } else {
            throw BlockedException ()
        }

        return moves
    }

    fun visit (func: (point: Point, thing: Thing) -> Unit) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val point = Point (row, col)
                val thing = thingAt (point)
                func (point, thing)
            }
        }
    }

    fun toGps (point: Point): Int = 100 * point.row + point.col

    fun dump () {
        visit { point, thing ->
            if (point.col == 0) {
                print (String.format ("%2d", point.row))
                print (": ")
            }
            if (point == robot) {
                print (Thing.ROBOT.symbol)
            } else {
                print(thing.symbol)
            }
            if (point.col == cols - 1) {
                println ()
            }
        }
        println (robot)
        return
    }
}

fun parse (input: String): Pair<Grid, List<Direction>> {
    val i = input.indexOf ("\n\n")

    // First parse out the maze

    val first = input.substring(0, i).trim().split ("\n")
    val rows = first.size
    val cols = first[0].length * 2
    val data = buildList {
        first.catenate().forEach {
            val thing = Thing.fromSymbol(it)
            if (thing == Thing.BOX) {
                add (Thing.BOX_LEFT)
                add (Thing.BOX_RIGHT)
            } else {
                add (thing)
                add (thing)
            }
        }
    }.toMutableList()
    val grid = Grid (rows, cols, data)

    // Then parse out the directions

    val second = input.substring (i + 1, input.length).replace ("\n", "").trim ()
    val moves = second.map { Direction.fromSymbol(it) }
    return grid to moves
}


