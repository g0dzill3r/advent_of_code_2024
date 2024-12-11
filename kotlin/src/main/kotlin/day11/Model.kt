package day11

import java.math.BigInteger

/**
 * The data model used to solve part1.
 */

open class Model (val input: String) {
    var stones = buildList {
        input.split (" ").forEach {
            add (it.toBigInteger())
        }
    }

    fun tick () {
        stones = buildList {
            stones.forEach {
                addAll (tick (it))
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

val BigInteger.isNegative: Boolean
    get () = this < BigInteger.ZERO

val BigInteger.sign: BigInteger
    get () = if (this < BigInteger.ZERO) -1.toBigInteger () else BigInteger.ONE

fun main () {
    listOf (-12312, -123122, -123, -12, -1, 0, 1, 12, 123, 1234)
        .map { it.toBigInteger () }
        .forEach {
//        println ("$it: ${Model.split (it)}")
        println ("[$it]")
        println (" - digits - ${Model.getDigits (it)}")
        val hasEvenDigits = Model.hasEvenDigits(it)
        println (" - hasEventDigits - $hasEvenDigits")
        if (hasEvenDigits) {
            println (" - split - ${Model.split (it)}")
        }

    }
}

// EOF