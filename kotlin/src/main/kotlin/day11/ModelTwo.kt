package day11

import util.increment
import java.math.BigInteger


/**
 * The data model used to solve part1.
 */

data class ModelTwo (val input: String) {
    var stones = mutableMapOf<BigInteger, BigInteger>().apply {
        input.split (" ").forEach {
            increment (it.toBigInteger ())
        }
    }

    fun tick () {
        stones = mutableMapOf<BigInteger, BigInteger>().apply {
            stones.forEach { (value, count) ->
                val repl = tick (value)
                repl.forEach {
                    increment (it, count)
                }
            }
        }
        return
    }

    fun tick (value: BigInteger): List<BigInteger> {
        return when {
            value == BigInteger.ZERO -> listOf (BigInteger.ONE)
            hasEvenDigits (value) -> split (value)
            else -> listOf (value.multiply (MULTIPLIER))
        }
    }

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

    override fun toString (): String = stones.toString ()
}

// EOF