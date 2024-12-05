package util



object BinaryUtil {
    fun possibilities (count: Int): Int = MathUtil.pow (2, count)

    fun toBinary (count: Int): String  {
        val str = MathUtil.pow (2, count) - 1
        return str.toString (2)
    }

    fun toBinarySeq (count: Int): Sequence<String> {
        return sequence {
            for (i in 0 until possibilities (count)) {
                val str = String.format ("%s", i.toString(2))
                yield (StringUtil.prefixToWidth (str, '0', count))
            }
        }
    }
}

fun main () {
    listOf (1, 2, 4, 8, 16, 24).forEach {
        println (String.format ("%24s", BinaryUtil.toBinary (it)))
    }
    val iter = BinaryUtil.toBinarySeq(4).iterator()
    while(iter.hasNext ()) {
        println (iter.next ())
    }
    return
}

// EOF