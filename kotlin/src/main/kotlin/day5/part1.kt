package day5

import util.InputUtil
import util.withInput

val DAY = 5
val SAMPLE = false

fun main () {
    withInput (DAY, SAMPLE) { input ->
        val rules = Rules.parse(input)

        var total = 0
        rules.updates.forEach {
            if (rules.isOrdered(it)) {
                total += it[it.size / 2]
            }
        }
        println(total)
    }
    return
}
