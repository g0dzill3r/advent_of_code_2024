package week3.day17

enum class Register (val operand: String, val literalValue: Int) {
    A ("a", 3),
    B ("b", 4),
    C ("c", 5);
    override fun toString () = operand
}

fun main () {
    Register.entries.forEach {
        println ("$it ~= ${it.literalValue}")
    }
    return
}

// EOF