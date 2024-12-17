package week3.day16.part2

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

    fun walk (start: Point, end: Point, dir: Direction): PathMap {
        val pathMap = PathMap (start, end)
        val initial = Rec (start, dir, 0, listOf (start to 0))

        walk (initial, end, pathMap)

        return pathMap
    }

    private fun walk (rec: Rec, end: Point, pathMap: PathMap) {

        // If we've reached the end then we can save our path (if it is lowest-cost)
        // and return

        if (rec.point == end) {
            pathMap.done (rec)
            return
        }

        // Let's try all the possible next moves from this location

        val possible = listOf (
            rec.forward,
            rec.left,
            rec.right
        )
        for (next in possible) {
            if (elementAt (next.point) == Element.EMPTY) {
                if (next.point !in rec.path.map { it.first }) {
                    walk (next, end, pathMap)
                }
            }
        }

        return
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
    val cost: Int = 0,
    val path: List<Pair<Point, Int>>
) {
    val position: Pair<Point, Direction>
        get () = Pair (point, dir)

    fun updatePath (next: Pair<Point, Int>): List<Pair<Point, Int>> {
        return buildList {
            addAll (path)
            add (next)
        }
    }

    val forward: Rec
        get () {
            val next = point.add (dir)
            val nextCost = cost + 1
            return Rec (next, dir, nextCost, updatePath (next to nextCost))
        }

    val left: Rec
        get () {
            val next = point.add (dir.left)
            val nextCost = cost + 1001
            return Rec (next, dir.left, nextCost, updatePath (next to nextCost))
        }

    val right: Rec
        get () {
            val next = point.add (dir.right)
            val nextCost = cost + 1001
            return Rec (next, dir.right, nextCost, updatePath (next to nextCost))
        }

    override fun toString (): String = "${point}->${dir} \$$cost\n [$path]"
}

data class PathMap (
    val start: Point,
    val end: Point,
) {
    val map: MutableMap<Pair<Point, Direction>, Int> = mutableMapOf ()
    var minCost: MutableMap<Point, Int> = mutableMapOf ()
    val paths: MutableList<List<Pair<Point, Int>>> = mutableListOf ()

    fun save (rec: Rec, reset: Boolean = false) {
        if (reset) {
            minCost.clear ()
        }
        rec.path.forEach { (point, cost) ->
            minCost[point] = cost
        }
        return
    }

    fun done (rec: Rec) {
        if (minCost[end] == null) {
            save (rec)
            paths.add (rec.path)
        } else {
            val min = minCost[end] as Int
            if (rec.cost < min) {
                save (rec, true)
                paths.clear ()
                paths.add (rec.path)
            } else if (rec.cost == min) {
                save (rec)
                paths.add (rec.path)
            }
        }
        return
    }
}

// EOF