package week3.day17

import kotlin.math.pow

fun Int.pow (power: Int): Int {
    return this.toDouble ().pow (power).toInt ()
}

fun Long.pow (power: Long): Long {
    return this.toDouble ().pow (power.toInt ()).toLong ()
}

infix operator fun kotlin.Int.div (den: Int): Int {
    val res = this.toDouble () / den
    val str = res.toString ()
    val i = str.indexOf (".")
    return str.substring (0, i).toInt ()
}

fun main () {
    listOf (0, 1, 2, 3, 4, 5).forEach {
        println ("$it: ${2.pow (it)}")
    }

    for (i in 0 until 64) {
        println ("$i / 8 = ${i / 8} ${i div 8}")
    }
}

// EOF