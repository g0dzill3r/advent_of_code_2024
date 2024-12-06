package util


object ListUtil {
    /**
     * Return all possible sublists of the specified list (including the empty list
     * and the list itself).
     */

    fun <T> subsets (list: List<T>): Sequence<List<T>> {
        return sequence {
            val count = list.size
            for (i in 0 until BinaryUtil.possibilities (count)) {
                val str = StringUtil.prefixToWidth (String.format ("%s", i.toString(2)), '0', count)
                val els = str
                val sublist = buildList {
                    els.forEachIndexed { i, el ->
                        if (el == '1') {
                            add (list[i])
                        }
                    }
                }
                yield (sublist)
            }
        }
    }

    fun <T> join (lists: List<List<T>>): List<T> {
        return buildList {
            lists.forEach {
                it.forEach {
                    add (it)
                }
            }
        }
    }
}

fun main () {
    val str = "abc"
    val iter = ListUtil.subsets(str.toList ()).iterator()
    while (iter.hasNext ()) {
        println (iter.next ())
    }
    return
}

// EOF