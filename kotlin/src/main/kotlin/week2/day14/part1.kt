package week2.day14

import util.increment
import util.repeat
import util.withInput

val DAY = 14
val SAMPLE = false

fun main () {
    println("day$DAY, part1")
    withInput(DAY, SAMPLE) { input ->
        val model = Model.parse(input)
        val clip = if (SAMPLE) {
            Vec2(11, 7)
        } else {
            Vec2(101, 103)
        }

        100.repeat {
            model.tick (clip)
        }

        val quadrants = clip.getQuadrants()
        val robots = mutableMapOf<Int, Int> ()
        model.robots.forEach { robot ->
            quadrants.forEachIndexed { i, quadrant ->
                if (robot.isIn (quadrant)) {
                    robots.increment (i)
                }
            }
        }

        var total = 1
        robots.values.forEach { total *= it }
        println (total)
    }
    return
}

// EOF