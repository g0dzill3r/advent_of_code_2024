package week1.day6

import util.ListUtil

enum class Direction (val deltaRow: Int, val deltaCol: Int) {
    N (-1, 0),
    E (0, 1),
    S (1, 0),
    W (0, -1);

    fun turn (): Direction {
        return when (this) {
            N -> E
            E -> S
            S -> W
            W -> N
        }
    }
}

enum class Thing (val symbol: Char) {
    EMPTY ('.'),
    OBSTRUCTION ('#'),
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

    private fun isValid (point: Point): Boolean = point.row in 0 until rows && point.col in 0 until cols

    private fun toIndex (point: Point): Int = point.row * cols + point.col

    fun thingAt (point: Point): Thing = data[toIndex (point)]

    fun update (point: Point, thing: Thing) {
        data[toIndex (point)] = thing
    }

    private fun markVisited (point: Point) = update (point, Thing.VISITED)
    fun obstruct (point: Point) = update (point, Thing.OBSTRUCTION)

    fun visit (func: (Point, Thing) -> Unit) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val point = Point(row, col)
                func (point, thingAt (point))
            }
        }
        return
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
        return thingAt (point) == Thing.OBSTRUCTION
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
    }

    override fun toString(): String {
        return buildString {
            visit { point, thing ->
                print (thing.symbol)
                if (point.col == cols - 1 && point.row != rows - 1) {
                    println ()
                }
            }
        }
    }
}

// EOF