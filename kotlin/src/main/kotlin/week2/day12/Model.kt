package week2.day12

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
    fun plantAt (point: Point): Char = data[toIndex(point)]

    fun visit (func: (point: Point, plant: Char) -> Unit) {
        for (y in 0 until rows) {
            for (x in 0 until cols) {
                val point = Point (x, y)
                func (point, plantAt (point))
            }
        }
    }

    fun dump () {
        visit { (x, y), plant ->
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
}

enum class Direction (val dx: Int, val dy: Int){
    N (0, -1),
    E (1, 0),
    S (0, 1),
    W (-1, 0)
}

data class Region (val plant: Char, val plots: List<Point>) {
    val area: Int = plots.size
    val price: Int = area * perimeter
    val perimeter: Int
        get () {
            var total = 0
            plots.forEach { plot ->
                Direction.entries.forEach { dir ->
                    if (plot.move (dir) !in plots) {
                        total ++
                    }
                }
            }
            return total
        }
}

// EOF