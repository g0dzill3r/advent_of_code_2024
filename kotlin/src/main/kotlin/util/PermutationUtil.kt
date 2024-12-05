package util

/**
 * I'm pretty sure I'm going to have to permute sets before long.
 */

object PermutationUtil {
    fun <T> permute (els: List<T>): Sequence<List<T>> {
        suspend fun SequenceScope<List<T>>.local (els: List<T>, prefix: List<T> = listOf ()) {
            when (els.size) {
                0 -> yield (prefix)
                else -> {
                    for (i in els.indices) {
                        val newPrefix = buildList {
                            addAll (prefix)
                            add (els[i])
                        }
                        val remainder = els.toMutableList().apply {
                            removeAt (i)
                        }
                        local (remainder, newPrefix)
                    }
                }
            }
        }
        return sequence {
            local (els)
        }
    }
}

fun main () {
    val things = "abc".toList ()
    val iter = PermutationUtil.permute (things).iterator ()
    while (iter.hasNext ()) {
        println (iter.next ())
    }
    return
}

// EOF