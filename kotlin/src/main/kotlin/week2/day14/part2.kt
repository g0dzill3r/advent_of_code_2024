package week2.day14

import util.withInput

fun main () {
    println("day$DAY, part2")
    withInput(DAY, SAMPLE) { input ->
        val model = Model.parse(input)
        val clip = if (SAMPLE) {
            Vec2(11, 7)
        } else {
            Vec2(101, 103)
        }

        var ticks = 0
        while (true) {
            if (model.isTreeLike (clip)) {
                break
            }
            model.tick (clip)
            ticks ++
        }
        println (ticks)
    }
    return
}

// EOF
