package week3.day21.part1

data class Code (val code: String) {
    override fun toString (): String = code

    val numeric: Int
        get () {
            val digits = code.filter { it.isDigit () }
            return buildString {
                digits.forEach { append (it) }
            }.toInt ()
        }

    companion object {
        fun parse (input: String) = Code (input)
        fun parseCodes (input: String): List<Code> = input.split ("\n").map { parse (it) }
    }
}

fun main () {

}

// EOF