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
    var cur: Position? = null

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
                else -> {
                    if (point == cur?.point) {
                        print ("C")
                    } else {
                        print (elementAt(point).render)
                    }
                }
            }
            if (point.col == cols - 1) {
                println ()
            }
        }
        return
    }

    /**
     * Start a set of walkers at the end of the maze in
     * each possible direction. The last spot has a cost of 0.
     */

    fun walk (graph: Map<Position, Node>) {
        var walkers = buildSet {
            Direction.entries.forEach { dir ->
                val pos = Position (end, dir)
                add (pos)
                val start = graph[pos] as Node
                start.cheapest = 0
            }
        }

        while (walkers.isNotEmpty()) {
            walkers = buildSet {
                walkers.forEach {
                    val cur = graph[it] as Node
                    cur.edges.forEach { edge ->
                        val next = edge.position
                        val node = graph[next] as Node
                        val cost = cur.cheapest!! + edge.cost
                        if (node.cheapest == null || cost < node.cheapest!!) {
                            node.cheapest = cost
                            add (next)
                        }
                    }
                }
            }
        }
        return
    }

    /**
     * Calculate the shortest paths through the maze
     */

//    fun shortest (graph: Map<Position, Node>): List<List<Point>> {
//        data class Walker (val pos: Position, val path: MutableList<Point> = mutableListOf ())
//        val walkers = mutableListOf (Walker (Position (start, Direction.EAST)))
//        while (walkers.isNotEmpty ()) {
//            walkers.forEach {
//                val node = graph[it.pos] as Node
//                val descents = node.edges.filter { i
//            }
//        }
//    }

    /**
     * Represents a location in the maze with edges to all possible moves
     * within the maze (turns and movement).
     */

    data class Node (
        val position: Position,
        val edges: MutableList<Edge> = mutableListOf (),
        var cheapest: Int? = null,
    ) {
        fun hasEdge (position: Position): Boolean = getEdge (position) != null
        fun getEdge (position: Position): Edge? {
            return edges.find { it.position == position }
        }

        override fun toString(): String {
            return buildString {
                append (position.toString ())
                append ("-")
                append (cheapest)
                append ("\n")
                edges.forEach {
                    append ("  - $it")
                }
            }
        }
    }

    /**
     * Represents an edge from one orientation to another and includes the
     * traversal cost.
     */

    data class Edge (
        val position: Position,
        val cost: Int
    ) {
        override fun toString (): String = "$position-$$cost"
    }

    /**
     * Represents a position and an orientation within the maze.
     */

    data class Position (val point: Point, val dir: Direction) {
        val left: Position
            get () = Position (point, dir.left)

        val right: Position
            get () = Position (point, dir.right)

        val forward: Position
            get () = Position (point.add (dir), dir)

        override fun toString (): String = "$point-${dir}"
    }

    /**
     * Bulds a graph representation of the maze including all the possible
     * orientations and the possible traversals between them.
     */

    fun buildGraph (graph: MutableMap<Position, Node> = mutableMapOf ()): MutableMap<Position, Node> {
        visit { point, el ->
            if (el == Element.EMPTY) {
                Direction.entries.forEach { dir ->
                    val position = Position (point, dir)
                    val node = graph.getOrPut (position) {
                        Node (position)
                    }
                    if (! node.hasEdge (position.left)) {
                        node.edges.add (Edge (position.left, 1000))
                    }
                    if (! node.hasEdge (position.right)) {
                        node.edges.add (Edge (position.right, 1000))
                    }
                    if (! node.hasEdge (position.forward) && elementAt (position.forward.point) == Element.EMPTY) {
                        node.edges.add (Edge (position.forward, 1))
                    }
                }
            }
        }
        return graph
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
    val visited: MutableMap<Pair<Point, Direction>, Boolean> = mutableMapOf ()
    var minCost: MutableMap<Point, Int> = mutableMapOf ()
    val paths: MutableList<List<Pair<Point, Int>>> = mutableListOf ()

    fun visit (point: Point, dir: Direction) {
        visited [point to dir] = true
    }
    fun isVisited (point: Point, dir: Direction): Boolean {
        return visited [point to dir] ?: false
    }

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