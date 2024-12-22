package week3.day17.part2

fun main () {
    fun p (a: Int) { println ("$a ${a.toString (2)}") }
    var a = 7 xor 401117
    p (a)
    a  = a xor 3
    p (a)
//    println (a.toLong ().binary)
//    println (((7 xor 401117) xor 3).toString (10))
    println (Long.MAX_VALUE._integer + " | " +Long.MAX_VALUE._binary)
    println (1L._integer + " | " + 1L._binary)
    return
}
val Long._integer: String
    get () {
        return String.format ("%019d", this)
    }


val Long._binary: String
    get () {
        val bits = this.toString (2)
        val str = "0".repeat (64 - bits.length) + bits
        return buildString {
            str.forEachIndexed { i, c ->
                if (i != 0 && i % 4 == 0) {
                    append (' ')
                }
                append (c)
            }
        }
    }