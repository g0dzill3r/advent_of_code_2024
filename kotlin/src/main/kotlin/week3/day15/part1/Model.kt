package week3.day15.part1

import week3.day15.Direction
import week3.day15.Point
import week3.day15.catenate

enum class Thing (val symbol: Char) {
    EMPTY ('.'),
    ROBOT ('@'),
    BOX ('O'),
    WALL ('#');

    companion object {
        private val fromSymbol = entries.associateBy { it.symbol }
        fun fromSymbol(symbol: Char) = fromSymbol[symbol] as Thing
    }
}


data class Grid (val rows: Int, val cols: Int, val data: MutableList<Thing>) {
    var robot = fromIndex (data.indexOf (Thing.ROBOT))

    init {
        update (robot, Thing.EMPTY)
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
            Thing.BOX -> {
                val empty = findEmpty (maybe, dir)
                if (empty != null) {
                    var cur = empty!!
                    while (cur != robot) {
                        val prev = cur.add (dir.opposite)
                        update (cur, thingAt (prev))
                        cur = prev
                    }
                    robot = maybe
                }
            }
            else -> throw IllegalStateException ()
        }
    }

    fun findEmpty (point: Point, dir: Direction): Point? {
        var pos = point
        while (true) {
            when (thingAt (pos)) {
                Thing.WALL -> return null
                Thing.BOX -> pos = pos.add (dir)
                Thing.EMPTY -> return pos
                else -> throw IllegalStateException ()
            }
        }
        // NOT REACHED
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
    val cols = first[0].length
    val data = first.catenate ().map { Thing.fromSymbol(it) }.toMutableList()
    val grid = Grid (rows, cols, data)

    // Then parse out the directions

    val second = input.substring (i + 1, input.length).replace ("\n", "").trim ()
    val moves = second.map { Direction.fromSymbol(it) }
    return grid to moves
}

// EOF