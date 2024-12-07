package day7

import util.BinaryUtil
import java.math.BigInteger

data class Equations (
    val equations: List<Equation>
) {
    companion object {
        fun parse (input: String): Equations {
            return Equations (
                input.split ("\n").map {
                    Equation.parse (it)
                }
            )
        }
    }
}

data class Equation (
    val output: BigInteger,
    val inputs: List<BigInteger>
) {
    fun isPossible (): Boolean {
        val options = BinaryUtil.toBinarySeq(inputs.size - 1).toList ()
        options.forEach {
            val ops = it.toList()
            var result = inputs[0]
            for (i in ops.indices) {
                val op = ops[i]
                when (op) {
                    '0' -> result += inputs[i + 1]
                    '1' -> result *= inputs[i + 1]
                }
            }
            if (result == output) {
                return true
            }
        }
        return false
    }

    fun isPossible2 (): Boolean {
        val options = TernaryUtil.toTernarySeq(inputs.size - 1).toList ()
        options.forEach {
            val ops = it.toList()
            var result = inputs[0]
            for (i in ops.indices) {
                val op = ops[i]
                when (op) {
                    '0' -> result += inputs[i + 1]
                    '1' -> result *= inputs[i + 1]
                    '2' -> result = "${result}${inputs[i+1]}".toBigInteger()
                }
            }
            if (result == output) {
                return true
            }
        }
        return false
    }

    override fun toString (): String {
        return "$output: $inputs"
    }

    companion object {
        fun parse (input: String): Equation {
            val i = input.indexOf (':')
            val output = input.substring(0, i).toBigInteger()
            val inputs = input.substring (i + 2, input.length).split (" ").map { it.toBigInteger() }
            return Equation (output, inputs)
        }
    }
}