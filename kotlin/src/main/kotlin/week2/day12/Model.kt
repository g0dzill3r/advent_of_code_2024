package week2.day12

import util.repeat

data class Point (val x: Int, val y: Int) {
    override fun toString (): String = "($x, $y)"
    fun move (dir: Direction) = Point (x + dir.dx, y + dir.dy)
    val neighbors: List<Point>
        get () {
            return buildList {
                Direction.entries.forEach {
                    add (move (it))
                }
            }
        }
}

data class Gardens (val input: String) {
    private val split = input.split ("\n")
    val rows = split.size
    val cols = split[0].length
    val data = input.replace ("\n", "")

    fun isValid (point: Point) = point.x in 0 until cols && point.y in 0 until rows
    fun toIndex (x: Int, y: Int) = y * rows + x
    fun toIndex (point: Point) = toIndex (point.x, point.y)
    fun plantAt (point: Point): Char {
        return if (isValid (point)) {
            data[toIndex(point)]
        } else {
            '.'
        }
    }

    fun visit (func: (point: Point, plant: Char) -> Unit) {
        for (y in 0 until rows) {
            for (x in 0 until cols) {
                val point = Point (x, y)
                func (point, plantAt (point))
            }
        }
    }

    fun dump () {
        visit { (x, _), plant ->
            print (plant)
            if (x == cols - 1) {
                println ()
            }
        }
    }

    fun getRegion (point: Point): Region {
        fun fillRegion (point: Point, plant: Char, plots: MutableList<Point> = mutableListOf()): List<Point> {
            if (isValid (point) && plantAt (point) == plant && point !in plots) {
                plots.add (point)
                point.neighbors.forEach {
                    fillRegion (it, plant, plots)
                }
            }
            return plots
        }
        val plant = plantAt(point)
        val plots = fillRegion (point, plant)
        return Region (plant, plots)
    }

    /**
     * Get all of the regions in the garden.
     */

    fun getRegions (): List<Region> {
        val possible = buildList {
            visit { point, _ ->
                add (point)
            }
        }.toMutableList ()

        return buildList {
            while (possible.isNotEmpty()) {
                val point = possible.removeFirst()
                val region = getRegion (point)
                add (region)
                possible.removeAll (region.plots)
            }
        }
    }

    inner class Region (val plant: Char, val plots: List<Point>) {
        val area: Int = plots.size
        val price: Int = area * perimeter
        val bulkPrice: Int = area * sides

        val perimeter: Int
            get() {
                var total = 0
                plots.forEach { plot ->
                    Direction.entries.forEach { dir ->
                        if (plot.move(dir) !in plots) {
                            total++
                        }
                    }
                }
                return total
            }

        val corners: List<Pair<Point, List<List<Direction>>>>
            get() {
                fun hasFence (p1: Point, p2: Point): Boolean {
                    return plots.contains (p1) != plots.contains (p2)
                }
                return buildList {
                    for (y in 0..rows) {
                        for (x in 0..cols) {
                            val p1 = Point(x, y)
                            val p2 = p1.move(Direction.N)
                            val p3 = p2.move(Direction.W)
                            val p4 = p3.move(Direction.S)
                            val fences = buildSet {
                                if (hasFence(p1, p2)) {
                                    add(Direction.E)
                                }
                                if (hasFence(p2, p3)) {
                                    add(Direction.N)
                                }
                                if (hasFence(p3, p4)) {
                                    add(Direction.W)
                                }
                                if (hasFence(p4, p1)) {
                                    add(Direction.S)
                                }
                            }
                            val corners = buildList {
                                if (fences.size == 4) {
                                    add (CORNERS[0])
                                    add (CORNERS[2])
                                } else {
                                    for (corner in CORNERS) {
                                        if (fences.containsAll(corner)) {
                                            add(corner)
                                        }
                                    }
                                }
                            }
                            add (p1 to corners)
                        }
                    }
                }
            }

        val sides: Int
            get() {
                var total = 0
                for ((_, list) in corners) {
                    total += list.size
                }
                return total
            }

        fun dump() {
            visit { point, _ ->
                if (point in plots) {
                    print(plant)
                } else {
                    print(".")
                }
                if (point.x == cols - 1) {
                    println()
                }
            }
            return
        }

        /**
         *
         */

        fun countCorners (): Int {
            var corners = 0

            fun plantAt (point: Point): Char {
                return if (point in plots) {
                    this@Gardens.plantAt(point)
                } else {
                    '.'
                }
            }

            fun corners (point: Point, match: (Char) -> Boolean): Int {
                var count = 0
                val (a, b, c, d) = Direction.entries.map { match (plantAt (point.move (it))) }
                if (a && b) count ++
                if (b && c) count ++
                if (c && d) count ++
                if (d && a) count ++
                return count
            }

            fun isCheckerboard (point: Point): Boolean {
                val match = listOf (
                    point,
                    point.move (Direction.E),
                    point.move (Direction.S),
                    point.move (Direction.S).move (Direction.E)
                ).map {
                    plantAt (it) == plant && plots.contains (it)
                }
                return match == listOf (true, false, false, true) || match == listOf (false, true, true, false)
            }

            visit { point, other ->

                // Check for convex corners

                if (plant == other && plots.contains (point)) {
                    corners += corners (point) { it != plant }
                }

                // Otherwise check for concave corners

                else if (plant != other) {
                    corners += corners (point) { it == plant }
                }

                // Deal with the overlapping cases as well

                if (isCheckerboard (point)) {
                    corners -= 2
                }
            }

            return corners
        }
    }

    companion object {
        private val CORNERS = buildList {
            Direction.entries.forEach { dir ->
                add (listOf (dir, dir.turn ()))
            }
        }
    }
}

enum class Direction (val dx: Int, val dy: Int){
    N (0, -1),
    E (1, 0),
    S (0, 1),
    W (-1, 0);

    fun turn (count: Int = 1): Direction {
        var dir = this
        count.repeat {
            dir = when (this) {
                N -> E
                E -> S
                S -> W
                W -> N
            }
        }
        return dir
    }
}


// EOF