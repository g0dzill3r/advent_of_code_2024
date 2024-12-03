package day2

import util.InputUtil

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    val data = Reports.parse (input)
    var total = 0

    data.reports.forEach {
        if (it.isSafe || it.isFixable) {
            total ++
        }
    }

    println (total)
    return
}

// EOF
