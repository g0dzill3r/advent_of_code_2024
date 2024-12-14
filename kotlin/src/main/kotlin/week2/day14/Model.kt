package week2.day14

import java.util.regex.Pattern

data class Vec2 (var x: Int, var y: Int) {
    fun add (delta: Vec2, clip: Vec2): Vec2 {
        val x = (x + delta.x + clip.x) % clip.x
        val y = (y + delta.y + clip.y) % clip.y
        return Vec2 (x, y)
    }
    override fun toString (): String = "($x,$y)"

    fun getQuadrants (): List<Pair<IntRange, IntRange>> {
        val midx = (x - 1) / 2
        val midy = (y - 1) / 2
        return buildList {
            add ((0 until midx) to (0 until midy))
            add ((midx + 1 until x) to (midy + 1 until y))
            add ((0 until midx) to (midy + 1 until y))
            add ((midx + 1 until x) to (0 until midy))
        }
    }
}

data class Robot (var pos: Vec2, var velocity: Vec2) {
    fun tick (clip: Vec2) {
        pos = pos.add (velocity, clip)
        return
    }

    fun isIn (range: Pair<IntRange, IntRange>): Boolean = pos.x in range.first && pos.y in range.second

    companion object {
        private val pattern = Pattern.compile ("p=(-?[0-9]+),(-?[0-9]+) v=(-?[0-9]+),(-?[0-9]+)")
        fun parse (input: String): Robot {
            val matcher = pattern.matcher (input)
            return if (!matcher.matches()) {
                throw IllegalArgumentException("Invalid input")
            } else {
                var i = 1
                val pos = Vec2 (matcher.group (i++).toInt (), matcher.group (i ++).toInt())
                val velocity = Vec2 (matcher.group (i++).toInt (), matcher.group (i ++).toInt())
                Robot (pos, velocity)
            }
        }
    }
}

data class Model (val robots: List<Robot>) {
    fun tick (dim: Vec2) {
        robots.forEach {
            it.tick (dim)
        }
        return
    }

    companion object {
        fun parse (input: String): Model {
            return Model (buildList {
                input.split ("\n").map {
                    add (Robot.parse (it))
                }
            })
        }
    }
}

// EOF