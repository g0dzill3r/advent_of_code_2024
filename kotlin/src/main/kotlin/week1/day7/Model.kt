package week1.day7

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

enum class Op (val symbol: String, val code: Char) {
    ADD ("+", '0'),
    MULTIPLY ("*", '1'),
    CATENATE ("||", '2');

    companion object {
        private val codeMap = entries.associateBy { it.code }
        fun fromCode (code: Char): Op = codeMap[code] as Op
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
                when (Op.fromCode (op)) {
                    Op.ADD -> result += inputs[i + 1]
                    Op.MULTIPLY -> result *= inputs[i + 1]
                    else -> Unit
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
                when (Op.fromCode (op)) {
                    Op.ADD -> result += inputs[i + 1]
                    Op.MULTIPLY -> result *= inputs[i + 1]
                    Op.CATENATE -> result = "${result}${inputs[i+1]}".toBigInteger()
                }
            }
            if (result == output) {
                return true
            }
        }
        return false
    }

    fun isPossibleWithCount(): List<Pair <List<BigInteger>, List<Char>>> {
        val options = BinaryUtil.toBinarySeq(inputs.size - 1).toList ()
        val save = mutableListOf<Pair <List<BigInteger>, List<Char>>> ()
        options.forEach {
            val ops = it.toList()
            var result = inputs[0]
            for (i in ops.indices) {
                val op = ops[i]
                when (Op.fromCode (op)) {
                    Op.ADD -> result += inputs[i + 1]
                    Op.MULTIPLY -> result *= inputs[i + 1]
                    else -> Unit
                }
            }
            if (result == output) {
                save.add (inputs to ops)
            }
        }
        return save
    }

    fun isPossible2WithCount(): List<Pair <List<BigInteger>, List<Char>>> {
        val options = BinaryUtil.toBinarySeq(inputs.size - 1).toList ()
        val save = mutableListOf<Pair <List<BigInteger>, List<Char>>> ()
        options.forEach {
            val ops = it.toList()
            var result = inputs[0]
            for (i in ops.indices) {
                val op = ops[i]
                when (Op.fromCode (op)) {
                    Op.ADD -> result += inputs[i + 1]
                    Op.MULTIPLY -> result *= inputs[i + 1]
                    Op.CATENATE -> result = "${result}${inputs[i+1]}".toBigInteger()
                }
            }
            if (result == output) {
                save.add (inputs to ops)
            }
        }
        return save
    }

    fun formatted (els: List<BigInteger>, ops: List<Char>): String {
        val iter = ops.iterator()
        return buildString {
            append (output)
            append (" = ")
            els.forEach {
                append(it)
                if (iter.hasNext()) {
                    append(" ${Op.fromCode(iter.next()).symbol} ")
                }
            }
        }
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