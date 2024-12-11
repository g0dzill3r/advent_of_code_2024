package day10

data class Point (val row: Int, val col: Int) {
    fun move (dir: Direction): Point {
        return Point (row + dir.deltaRow, col + dir.deltaCol)
    }
    val neighbors: List<Point>
        get () = buildList {
            Direction.entries.forEach { dir ->
                add (this@Point.move (dir))
            }
        }

    override fun toString (): String = "($row, $col)"
}

enum class Direction (val deltaRow: Int, val deltaCol: Int) {
    N (-1, 0),
    E (0, 1),
    S (1, 0),
    W (0, -1)
}

data class Grid (val input: String) {
    private val split = input.split ("\n")
    val data = input.replace ("\n", "").map { it.code - '0'.code}
    val rows = split.size
    val cols = split[0].length

    fun isValid (point: Point): Boolean = point.row in 0 until rows && point.col in 0 until cols
    fun toIndex (point: Point): Int = point.row * rows + point.col
    fun valueAt (point: Point): Int = data[toIndex (point)]

    fun visit (func: (Point, Int) -> Unit) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val point = Point (row, col)
                func (point, valueAt (point))
            }
        }
        return
    }

    fun dump () {
        visit { p, h ->
            print (h)
            if (p.col == cols - 1) {
                println ()
            }
        }
        return
    }

    val trailheads: List<Point>
        get () {
            return buildList {
                visit { point, height ->
                    if (height == 0) {
                        add (point)
                    }
                }
            }
        }

    fun neighbors (point: Point): List<Point> = point.neighbors.filter { isValid (it) }

    fun getHikes (cur: Point, path: MutableList<Point> = mutableListOf (), complete: MutableList<List<Point>> = mutableListOf ()): List<List<Point>> {

        // Add ourselves to the path

        val height = valueAt (cur)
        path.add (cur)

        // Are we done (reached height 9)

        if (height == 9) {
            complete.add (path)
            return complete
        }

        // See if there are any neighbors with the right height

        val neighbors = neighbors (cur).filter {
            valueAt (it) == height + 1
        }
        neighbors.forEach {
            getHikes(it, mutableListOf<Point>().apply { addAll(path) }, complete)
        }

        return complete
    }

    fun dumpHike (hike: List<Point>): String {
        return buildString {
            hike.forEachIndexed { i, point ->
                append (point)
                append (valueAt (point))
                if (i != hike.size - 1) {
                    append ("-")
                }
            }
        }

    }
}

// EOF