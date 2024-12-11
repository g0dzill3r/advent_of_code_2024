package week2.day8

/**
 * An update to the Grid class to obey the resonance rules of part2 of the problem.
 */

class Grid2 (input: String) : Grid(input) {
    override fun getAntinodes (which: Char, set: MutableSet<Point>): Set<Point> {
        val pairs = getAntennaPairs(which)
        pairs.forEach { (a, b) ->
            val deltaRow = a.row - b.row
            val deltaCol = a.col - b.col

            var point = a
            while (true) {
                point = point.add(deltaRow, deltaCol)
                if (isValid(point)) {
                    set.add(point)
                } else {
                    break
                }
            }

            point = b
            while (true) {
                point = point.add(-deltaRow, -deltaCol)
                if (isValid(point)) {
                    set.add(point)
                } else {
                    break
                }
            }
        }
        return set
    }

    override fun getAntinodes(): Set<Point> {
        val types = getAntennaTypes()
        val points = mutableSetOf<Point>()
        types.forEach {
            getAntinodes (it, points)
        }
        visit { point, thing ->
            if (thing is Thing.Antenna) {
                points.add(point)
            }
        }
        return points
    }
}

// EOF