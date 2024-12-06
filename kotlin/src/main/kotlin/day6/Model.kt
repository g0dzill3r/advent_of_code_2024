package day6

import util.ListUtil

enum class Direction (val deltaRow: Int, val deltaCol: Int) {
//    NW (-1, -1),
    N (-1, 0),
//    NE (1, -1),
    E (0, 1),
//    SE (1, 1),
    S (1, 0),
//    SW (1, -1),
    W (0, -1);

    fun turn (): Direction {
        return when (this) {
            Direction.N -> Direction.E
            Direction.E -> Direction.S
            Direction.S -> Direction.W
            Direction.W -> Direction.N
        }
    }
}

enum class Thing (val symbol: Char) {
    EMPTY ('.'),
    THING ('#'),
    GUARD ('^'),
    VISITED ('X');

    companion object {
        private val map = entries.associateBy(Thing::symbol)
        fun from(symbol: Char) = map[symbol] ?: throw Exception ("Unrecognized: $symbol")
    }
}

data class Point (val row: Int, val col: Int) {
    fun move (dir: Direction): Point {
        return Point (row + dir.deltaRow, col + dir.deltaCol)
    }
    override fun toString (): String = "($row, $col)"
}

data class Lab (
    val data: MutableList<Thing>,
    val rows: Int,
    val cols: Int,
    var guard: Point
) {
    var dir: Direction = Direction.N

    val visitCount: Int
        get () = data.filter { it == Thing.VISITED }.size

    fun isValid (row: Int, col: Int): Boolean {
        return row in 0 until rows && col in 0 until cols
    }
    fun isValid (point: Point): Boolean = isValid (point.row, point.col)

    private fun toIndex (row: Int, col: Int): Int = row * cols + col
    private fun toIndex (point: Point): Int = toIndex (point.row, point.col)

    fun thingAt (row: Int, col: Int): Thing = data[toIndex (row, col)]
    fun thingAt (point: Point): Thing = data[toIndex (point)]

    fun update (point: Point, thing: Thing) {
        data[toIndex (point)] = thing
    }

    fun visit (func: (Int, Int, Thing) -> Unit) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                func (row, col, thingAt (row, col))
            }
        }
        return
    }

    private fun findGuard (): Point {
        var found: Point? = null
        visit { row, col, thing ->
            if (thing == Thing.GUARD) {
                if (found != null) {
                    throw Exception("Two guards!?")
                }
                found = Point (row, col)
            }
        }
        return if (found == null) {
            throw Exception("Guard not found.")
        } else {
            found !!
        }
    }

    fun markVisited (point: Point) {
        data[point.row * cols + point.col] = Thing.VISITED
    }

    companion object {
        val OUTSIDE = Point(-1, -1)

        fun parse (input: String): Lab {
            val data = input.split("\n").map {
                it.map { Thing.from(it) }
            }
            val rows = data.size
            val cols = data[0].size
            val joined = ListUtil.join(data).toMutableList()
            val guard = joined.indexOf (Thing.GUARD)
            joined[guard] = Thing.VISITED
            return Lab(joined, rows, cols, Point(guard / cols, guard % cols))
        }
    }

    private fun isObstructed (point: Point): Boolean {
        return thingAt (point) == Thing.THING
    }

    fun step (): Pair<Point, Direction>? {
        var blocked: Pair<Point, Direction>? = null
        if (! isValid (guard.move (dir))) {
            return OUTSIDE to dir
        }
        while (isObstructed (guard.move (dir))) {
            if (blocked == null) {
                blocked = guard to dir
            }
            dir = dir.turn()
        }
        val next = guard.move (dir)
        guard = next
        markVisited (next)
        return blocked
    }

    fun patrol () {
        val seen = mutableSetOf<Pair<Point, Direction>> ()
        while (true) {
            val blocked = step ()
            if (blocked != null) {
                if (blocked.first == OUTSIDE) {
                    return
                }
                if (seen.contains (blocked)) {
                    throw Exception ("Looping.")
                }
                seen.add (blocked)
            }
        }
        return
    }

    override fun toString(): String {
        return buildString {
            visit { row, col, thing ->
                print (thing.symbol)
                if (col == cols - 1 && row != rows - 1) {
                    println ()
                }
            }
        }
    }
}

// EOF