package util

import kotlin.math.exp

object MathUtil {
    fun pow (a: Int, b: Int): Int {
        return Math.pow (a.toDouble(), b.toDouble()).toInt()
    }
}

fun main () {
    println (MathUtil.pow (2, 8))
    println (MathUtil.pow (2, 16))
    println (MathUtil.pow (2, 24))
    println (MathUtil.pow (2, 32))
    return
}