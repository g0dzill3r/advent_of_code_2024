package week2.day11

import util.increment
import java.math.BigInteger


/**
 * The data model used to solve part1.
 */

data class ModelTwo (val input: String) : BaseModel<BigInteger>() {
    var stones = mutableMapOf<BigInteger, BigInteger>().apply {
        input.split (" ").forEach {
            increment (it.toBigInteger ())
        }
    }

    override fun tick () {
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

    override val stoneCount: BigInteger
        get () {
            var total = BigInteger.ZERO
            stones.forEach { (value, count) ->
                total += count
            }
            return total
        }

    override fun toString (): String = stones.toString ()
}

// EOF