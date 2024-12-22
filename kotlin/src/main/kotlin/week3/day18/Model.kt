package week3.day18

import util.repeat

data class Point (val row: Int, val col: Int) {
    fun move (dir: Direction) = Point (row + dir.delta.row, col + dir.delta.col)
    override fun toString () = "($row, $col)"

    companion object {
        fun parse (input: String): Point {
            val (col, row) = input.split(",")
            return Point (row.toInt(), col.toInt())
        }
    }
}

enum class Direction (val symbol: Char, val delta: Point) {
    UP ('^', Point (-1, 0)),
    DOWN ('v', Point (1, 0)),
    LEFT ('<', Point (0, -1)),
    RIGHT ('>', Point (0, 1));
}

enum class State (val symbol: Char) {
    WORKING ('.'),
    DAMAGED ('#')
}

data class Memory (val rows: Int, val cols: Int, val seq: List<Point>) {
    var next = 0
    var memory = mutableListOf<State> ()
    var steps = mutableListOf<MutableList<Int>> ()
    var start = Point (0, 0)
    val end = Point (rows - 1, cols - 1)

    init {
        reset ()
    }

    private fun reset () {
        memory = buildList {
            (rows * cols).repeat {
                add (State.WORKING)
            }
        }.toMutableList()
        steps = buildList {
            (rows * cols).repeat {
                add (mutableListOf<Int>())
            }
        }.toMutableList()
    }

    fun isValid (point: Point) = point.row in 0 until rows && point.col in 0 until cols
    fun toIndex (row: Int, col: Int): Int = col + row * cols
    fun toIndex (point: Point): Int = toIndex (point.row, point.col)
    fun get (point: Point): State = memory [toIndex (point)]
    fun set (point: Point, state: State) {
        memory [toIndex (point)] = state
        return
    }

    fun visit (func: (Point, State) -> Unit) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val point = Point (row, col)
                val state = get (point)
                func (point, state)
            }
        }
    }

    fun dump () {
        visit { point, state ->
            when {
                state == State.DAMAGED -> print ('#')
                steps[toIndex (point)].isEmpty() -> print ('.')
                else -> print (steps[toIndex (point)].size % 10)
            }
            if (point.col == cols - 1) {
                println ()
            }
        }
        return
    }

    fun tick (): Point? {
        return if (next < seq.size) {
            val point = seq[next ++]
            set (point, State.DAMAGED)
            point
        } else {
            null
        }
    }

    fun walk (): Int {
        val start = Point (0, 0)
        var walkers = setOf (start)
        var step = 0
        steps[toIndex (start)].add (step)

        // Roam around until someone finds their way to the exit

        while (steps[toIndex (end)].isEmpty ()) {
            step ++
            walkers = buildSet {
                walkers.forEach { walker ->
                    Direction.entries.forEach { dir ->
                        val next = walker.move (dir)
                        if (isValid (next)) {
                            if (get (next) == State.WORKING) {
                                steps[toIndex (next)].add (step)
                                add (next)
                            }
                        }
                    }
                }
            }
        }

        return steps[toIndex (end)][0]
    }

    /**
     * Determine whether the specified point is reachable from the start
     * given the current state of memory corruption.
     */

    fun reachable (point: Point): Boolean {
        val reached = mutableSetOf<Point> (start)
        var walkers = setOf<Point> (start)

        while (walkers.isNotEmpty() && point !in reached) {
            walkers = buildSet {
                walkers.forEach { walker ->
                    Direction.entries.forEach { dir ->
                        val next = walker.move (dir)
                        if (isValid (next) && next !in reached && get (next) == State.WORKING) {
                            reached.add (next)
                            add (next)
                        }
                    }
                }
            }
        }

        return point in reached
    }

    companion object {
        fun parse ( rows: Int, cols: Int, input: String): Memory {
            val seq = buildList {
                input.split ("\n").forEach {
                    add (Point.parse (it))
                }
            }
            return Memory (rows, cols, seq)
        }
    }
}