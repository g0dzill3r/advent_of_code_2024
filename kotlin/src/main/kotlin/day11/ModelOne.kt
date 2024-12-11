package day11

import java.math.BigInteger

class ModelOne (input: String): BaseModel<Int> () {
    var stones = buildList {
        input.split (" ").forEach {
            add (it.toBigInteger())
        }
    }

    override fun tick () {
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

    override val stoneCount: Int = stones.size
    override fun toString (): String = stones.toString ()
}

// EOF