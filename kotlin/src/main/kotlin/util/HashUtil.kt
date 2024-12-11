package util

import java.math.BigInteger

fun <T> MutableMap<T, Int>.increment (key: T, count: Int = 1): Int {
    val value = (get (key) ?: 0) + count
    put (key, value)
    return value
}

fun <T> MutableMap<T, BigInteger>.increment (key: T, count: BigInteger = BigInteger.ONE): BigInteger {
    val value = (get (key) ?: BigInteger.ZERO).add (count)
    put (key, value)
    return value
}

// EOF