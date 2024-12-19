package week3.day17

data class Operand (val literalValue: Long) {
    fun comboValue (computer: Computer): Long {
        return when (literalValue) {
            0L, 1L, 2L, 3L -> literalValue
            4L -> computer.getRegister(Register.A)
            5L -> computer.getRegister(Register.B)
            6L -> computer.getRegister(Register.C)
            7L -> throw IllegalStateException("Reserved.")
            else -> throw IllegalStateException("Invalid combo value: $literalValue")
        }
    }

    override fun toString (): String {
        when (literalValue) {
            0L, 1L, 2L, 3L, 7L -> return "$literalValue"
            4L, 5L, 6L -> return "$literalValue [${listOf ("a", "b", "c")[literalValue.toInt () - 4]}]"
            else -> throw IllegalStateException ("Invalid literal value: $literalValue")
        }
    }

    companion object {
        fun parse(input: Long): Operand = Operand(input)
    }
}

// EOF