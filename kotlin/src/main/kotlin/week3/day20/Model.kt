package week3.day20

import kotlin.math.abs

enum class Element (val symbol: Char) {
    EMPTY ('.'),
    WALL ('#'),
    START ('S'),
    END ('E');

    companion object {
        private val map = entries.associateBy { it.symbol }
        fun fromSymbol(symbol: Char) = map[symbol] !!
    }
}

data class Point (val row: Int, val col: Int) {
    override fun toString() = "($row, $col)"
    fun move (dir: Direction): Point = Point (row + dir.delta.row, col + dir.delta.col)
}

enum class Direction (val symbol: Char, val delta: Point) {
    UP ('^', Point (-1, 0)),
    RIGHT ('>', Point (0, 1)),
    DOWN ('v', Point (1, 0)),
    LEFT ('<', Point (0, -1));

    val left: Direction
        get () = when (this) {
            UP -> LEFT
            LEFT -> DOWN
            DOWN -> RIGHT
            RIGHT -> UP
        }

    val right: Direction
        get () = when (this) {
            UP -> RIGHT
            LEFT -> UP
            DOWN -> LEFT
            RIGHT -> DOWN
        }
}


data class Maze (val rows: Int, val cols: Int, val data: MutableList<Element>) {
    val start = toPoint (data.indexOf (Element.START))
    val end = toPoint (data.indexOf (Element.END))

    init {
        update (start, Element.EMPTY)
        update (end, Element.EMPTY)
    }

    fun toIndex (row: Int, col: Int): Int = row * cols + col
    fun toIndex (point: Point): Int = toIndex (point.row, point.col)
    fun toPoint (index: Int): Point = Point (index / cols, index % cols)
    fun elementAt (point: Point): Element = data[toIndex (point)]
    fun update (point: Point, element: Element) {
        data[toIndex (point)] = element
        return
    }

    fun visit (func: (Point, Element) -> Unit) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val point = Point (row, col)
                val element = elementAt (point)
                func (point, element)
            }
        }
    }

    fun dump () {
        visit { point, element ->
            print (element.symbol)
            if (point.col == cols - 1) {
                println ()
            }
        }
        return
    }

    private fun isExteriorWall (point: Point): Boolean {
        return point.col == 0 || point.row == 0 || point.col == cols - 1 || point.row == rows - 1
    }

    private fun isWall (point: Point): Boolean = elementAt (point) == Element.WALL
    private fun isEmpty (point: Point): Boolean = elementAt (point) == Element.EMPTY

    private fun isCheatable (point: Point): Boolean {
        return false
    }

    val possibleCheats: List<Pair<Point, Point>>
        get () {
            return buildList {
                visit { point, element ->
                    if (element == Element.WALL && ! isExteriorWall(point)) {
                        val up = point.move (Direction.UP)
                        val down = point.move (Direction.DOWN)
                        if (isEmpty (up) && isEmpty (down)) {
                            add (up to down)
                        }
                        val left = point.move (Direction.LEFT)
                        val right = point.move (Direction.RIGHT)
                        if (isEmpty (left) && isEmpty (right)) {
                            add (left to right)
                        }
                    }
                }
            }
        }

    val startingDirection: Direction
        get () {
            Direction.entries.forEach { dir ->
                if (elementAt (start.move (dir)) == Element.EMPTY) {
                    return dir
                }
            }
            throw IllegalStateException ()
        }

    val graph: MazeGraph
        get () {
            val list = buildList {
                var cur = start
                var dir = startingDirection

                while (cur != end) {
                    add (cur)
                    if (isEmpty (cur.move (dir))) {
                        cur = cur.move (dir)
                    } else if (isEmpty (cur.move (dir.left))) {
                        cur = cur.move (dir.left)
                        dir = dir.left
                    } else if (isEmpty (cur.move (dir.right))) {
                        cur = cur.move (dir.right)
                        dir = dir.right
                    } else {
                        throw IllegalStateException ()
                    }
                }
                add (end)
            }
            return MazeGraph (list)
        }

    companion object {
        fun parse (input: String): Maze {
            val split = input.split ("\n")
            val rows = split.size
            val cols = split[0].length
            val data = input.replace ("\n", "").map { Element.fromSymbol (it) }.toMutableList()
            return Maze (rows, cols, data)
        }
    }
}

data class MazeGraph (val positions: List<Point>) {
    val index = buildMap {
        positions.forEachIndexed { index, point ->
            put (point, index)
        }
    }

    fun race (cheat: Pair<Point, Point>? = null): Int {
        return if (cheat != null) {
            val (p1, p2) = cheat
            val i1 = index [p1] as Int
            val i2 = index [p2] as Int
            positions.size - abs (i1 - i2) + 1
        } else {
            positions.size - 1
        }
    }
}

// EOF