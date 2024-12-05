package day5

import util.InputUtil

val DAY = 5
val SAMPLE = false

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    val rules = Rules.parse (input)

    var total = 0
    rules.updates.forEach {
        if (rules.isOrdered (it)) {
            total += it[it.size / 2]
        }
    }
    println (total)
    return
}
