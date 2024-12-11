package day11

import java.math.BigInteger

/**
 * The data model used to solve part1.
 */

abstract class BaseModel<T> () {
    abstract fun tick ()
    abstract val stoneCount: T

    companion object {
        val MULTIPLIER = 2024.toBigInteger ()

        fun getDigits (value: BigInteger): String {
            val str = value.toString()
            return when {
                value.isNegative -> str.substring (1, str.length)
                else -> str
            }
        }

        private fun countDigits (value: BigInteger): Int = getDigits (value).length
        fun hasEvenDigits(value: BigInteger): Boolean = countDigits(value) % 2 == 0

        fun split(value: BigInteger): List<BigInteger> {
            val str = getDigits (value)
            val digits = str.length
            return listOf(
                str.substring(0, digits / 2).toBigInteger().multiply (value.sign),
                str.substring(digits / 2, digits).toBigInteger()
            )
        }
    }
}



val BigInteger.isNegative: Boolean
    get () = this < BigInteger.ZERO

val BigInteger.sign: BigInteger
    get () = if (this < BigInteger.ZERO) -1.toBigInteger () else BigInteger.ONE

// EOF