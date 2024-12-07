package day7

import util.MathUtil
import util.StringUtil

object TernaryUtil {
    fun possibilities (count: Int): Int = MathUtil.pow (3, count)

    fun toTernary (count: Int): String  {
        val str = MathUtil.pow (3, count) - 1
        return str.toString (3)
    }

    fun toTernarySeq (count: Int): Sequence<String> {
        return sequence {
            for (i in 0 until possibilities (count)) {
                val str = String.format ("%s", i.toString(3))
                yield (StringUtil.prefixToWidth (str, '0', count))
            }
        }
    }
}

fun main () {
    listOf (1, 3, 9, 27, 81).forEach {
        println (String.format ("%24s", TernaryUtil.toTernary (it)))
    }
    val iter = TernaryUtil.toTernarySeq(9).iterator()
    while(iter.hasNext ()) {
        println (iter.next ())
    }
    return
}


// EOF