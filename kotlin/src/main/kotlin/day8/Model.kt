package day8

import kotlin.math.abs

sealed class Thing (val encoded: Char) {
    class Antenna (which: Char) : Thing (which)
    class Empty : Thing ('.')

    companion object {
        fun parse(c: Char): Thing {
            return if (c == '.') {
                Empty ()
            } else {
                Antenna (c)
            }
        }
    }
}

data class Point (val row: Int, val col: Int) {
    fun add (dr: Int, dc: Int) = Point (row + dr, col + dc)
}

class Grid (
    val data: List<Thing>,
    val rows: Int,
    val cols: Int
) {
    fun toIndex (point: Point) = point.row * cols + point.col
    fun isValid (point: Point) = point.row in 0 until rows && point.col in 0 until cols
    fun thingAt (point: Point): Thing = data[toIndex (point)]

    fun visit (func: (point: Point, thing: Thing) -> Unit) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val point = Point (row, col)
                func (point, thingAt (point))
            }
        }
    }

    override fun toString (): String {
        return dump ()
    }

    fun dump (mark: Set<Point> = emptySet ()): String {
        return buildString {
            visit { (row, col), thing ->
                if (mark.contains(Point(row, col))) {
                    append ('#')
                } else {
                    append(thing.encoded)
                }
                if (col == cols - 1 && row != rows - 1) {
                    append("\n")
                }
            }
        }
    }

    fun getAntennaTypes (): Set<Char> {
        return buildSet {
            visit { _, thing ->
                if (thing !is Thing.Empty) {
                    add(thing.encoded)
                }
            }
        }
    }

    fun getAntennas (which: Char): List<Point> {
        return buildList {
            visit { point, thing ->
                if (thing is Thing.Antenna) {
                    if (thing.encoded == which) {
                        add (point)
                    }
                }
            }
        }
    }

    fun getAntennaPairs (which: Char): List<Pair<Point, Point>> {
        return PairUtil.getPairs (getAntennas (which))
    }

    fun getAntinodes (which: Char, set: MutableSet<Point> = mutableSetOf ()): Set<Point> {
        val pairs = getAntennaPairs (which)
        pairs.forEach { (a, b) ->
            val deltaRow = a.row - b.row
            val deltaCol = a.col - b.col
            val antiA = a.add (deltaRow, deltaCol)
            if (isValid (antiA)) {
                set.add (antiA)
            }
            val antiB = b.add (-deltaRow, -deltaCol)
            if (isValid (antiB)) {
                set.add (antiB)
            }
        }
        return set
    }

    fun getAntinodes2 (which: Char, set: MutableSet<Point> = mutableSetOf ()): Set<Point> {
        val pairs = getAntennaPairs (which)
        pairs.forEach { (a, b) ->
            val deltaRow = a.row - b.row
            val deltaCol = a.col - b.col

            var point = a
            while (true){
                point = point.add (deltaRow, deltaCol)
                if (isValid (point)) {
                    set.add (point)
                } else {
                    break
                }
            }

            point = b
            while (true) {
                point = point.add (-deltaRow, -deltaCol)
                if (isValid (point)) {
                    set.add (point)
                } else {
                    break
                }
            }
        }
        return set
    }

    fun getAntinodes (): Set<Point> {
        val types = getAntennaTypes ()
        val points = mutableSetOf<Point> ()
        types.forEach {
            getAntinodes(it, points)
        }
        return points
    }

    fun getAntinodes2 (): Set<Point> {
        val types = getAntennaTypes ()
        val points = mutableSetOf<Point> ()
        types.forEach {
            getAntinodes2(it, points)
        }
        visit { point, thing ->
            if (thing is Thing.Antenna) {
                points.add (point)
            }
        }
        return points
    }

    companion object {
        fun parse (input: String): Grid {
            val rows = input.split ("\n")
            val cols = rows[0].length
            val data = input.replace ("\n", "").map { Thing.parse (it)  }
            return Grid (data, rows.size, cols)
        }
    }
}

object PairUtil {
    fun <T> getPairs (list: List<T>): List<Pair<T, T>> {
        return buildList {
            for (i in list.indices) {
                for (j in i + 1 until list.size) {
                    add (list[i] to list[j])
                }
            }
        }
    }
}

// EOF