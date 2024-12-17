package week3.day17

data class Operand (val literalValue: Int) {
    fun comboValue (computer: Computer): Int {
        return when (literalValue) {
            0, 1, 2, 3 -> literalValue
            4 -> computer.getRegister(Register.A)
            5 -> computer.getRegister(Register.B)
            6 -> computer.getRegister(Register.C)
            7 -> throw IllegalStateException("Reserved.")
            else -> throw IllegalStateException("Invalid combo value: $literalValue")
        }
    }

    override fun toString (): String {
        when (literalValue) {
            0, 1, 2, 3, 7 -> return "$literalValue"
            4, 5, 6 -> return "$literalValue [${listOf ("a", "b", "c")[literalValue.toInt () - 4]}]"
            else -> throw IllegalStateException ("Invalid literal value: $literalValue")
        }
    }

    companion object {
        fun parse(input: Int): Operand = Operand(input)
    }
}

// EOF