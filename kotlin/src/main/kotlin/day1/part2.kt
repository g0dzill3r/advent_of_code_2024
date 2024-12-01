package day1

import util.InputUtil

fun main() {
    val input = InputUtil.getInput (DAY, SAMPLE);
    val (left, right) = parseLists(input);
    var total = 0;
    left.forEach { lval ->
        val count = right.filter { lval == it }.count()
        total += lval * count;
    }
    println (total);
    return;
}

// EOF