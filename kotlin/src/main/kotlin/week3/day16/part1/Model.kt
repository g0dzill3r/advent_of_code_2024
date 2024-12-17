package week3.day16.part1

import week3.day15.catenate
import week3.day16.Direction
import week3.day16.Element
import week3.day16.Point

data class Maze (
    val rows: Int,
    val cols: Int,
    val data: MutableList<Element>
) {
    fun toIndex (row: Int, col: Int): Int = row * cols + col
    fun toIndex (point: Point): Int = toIndex (point.row, point.col)
    fun fromIndex (index: Int): Point = Point (index / cols, index % cols)
    fun elementAt (point: Point): Element = data[toIndex (point.row, point.col)]
    fun update (point: Point, el: Element) {
        data[toIndex (point)] = el
        return
    }

    fun find (match: Element): Point? {
        for (col in 0 until cols) {
            for (row in 0 until rows) {
                val point = Point (row, col)
                if (elementAt (point) == match) {
                    return point
                }
            }
        }
        return null
    }

    val end = find (Element.END)!!
    val start = find (Element.START)!!

    init {
        update (start, Element.EMPTY)
        update (end, Element.EMPTY)
    }

    fun visit (func: (Point, Element) -> Unit) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val point = Point (row, col)
                func (point, elementAt (point))
            }
        }
    }

    fun dump () {
        visit { point, el ->
            if (point.col == 0) {
                print (String.format ("%2d: ", point.row))
            }
            when {
                point == start -> print (Element.START.render)
                point == end -> print (Element.END.render)
                else -> print (elementAt (point).render)
            }
            if (point.col == cols - 1) {
                println ()
            }
        }
        return
    }

    /**
     * Calculate the cheapest path from (start, end).
     */

    fun walk (start: Point, end: Point, dir: Direction): Int {
        val pathMap = PathMap (start, end)
        val check = buildList {
            add (Rec (start, dir, 0))
        }

        var next = check.toSet ()
        while (next.isNotEmpty ()) {
            next = buildSet {
                next.forEach {
                    val results = walk (it, end, pathMap)
                    this@buildSet.addAll (results)
                }
            }
        }

        return pathMap.min (end)
    }

    private fun walk (rec: Rec, end: Point, pathMap: PathMap): Set<Rec> {
        val results = mutableSetOf<Rec> ()

        // Now that we've arrived at the specified point (and orientation) let's calculate
        // the cost and track it in the pathMap

        if (pathMap.record (rec) != PathMap.Outcome.CONTINUE) {
            return results
        }

        // Let's try all the possible next moves from this location

        val possible = listOf (
            rec.forward,
            rec.left,
            rec.right
        )
        for (maybe in possible) {
            if (elementAt (maybe.point) == Element.EMPTY) {
                results.add (maybe)
            }
        }

        return results
    }

    companion object {
        fun parse (input: String): Maze {
            val split = input.split ("\n")
            val rows = split.size
            val cols = split[0].length
            val data = split.catenate().map { Element.fromSymbol(it) }.toMutableList()
            return Maze (rows, cols, data)
        }
    }
}

data class Rec (
    val point: Point,
    val dir: Direction,
    val cost: Int = 0
) {
    val position: Pair<Point, Direction>
        get () = Pair (point, dir)

    val forward: Rec
        get () = Rec (point.add (dir), dir, cost + 1)

    val left: Rec
        get () = Rec (point.add (dir.left), dir.left, cost + 1001)

    val right: Rec
        get () = Rec (point.add (dir.right), dir.right, cost + 1001)

    override fun toString (): String = "${point}->${dir} \$$cost"
}

data class PathMap (
    val start: Point,
    val end: Point,
) {
    val map: MutableMap<Pair<Point, Direction>, Int> = mutableMapOf ()

    enum class Outcome {
        CONTINUE, DISCONTINUE
    }

    fun min (point: Point): Int {
        val endings = buildList {
            Direction.entries.forEach { dir ->
                val found = map[end to dir]
                if (found != null) {
                    add (found)
                }
            }
        }
        return endings.min ()
    }

    fun record (rec: Rec): Outcome {
        val already = map [rec.position]
        if (already != null) {
            if (rec.cost >= already) {
                return Outcome.DISCONTINUE
            }
        }
        map[rec.position] = rec.cost
        return Outcome.CONTINUE
    }

}

// EOF