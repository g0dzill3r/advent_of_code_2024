package util

fun <X, R> ((X) -> R).memoized(): (X) -> R {
    val cache = mutableMapOf<X, R>()
    return { x -> cache.getOrPut(x) { this(x) } }
}

fun main () {
    fun double (i: Int): Int {
        Thread.sleep (1000)
        return i * 2
    }
    val d2 = ::double.memoized ()

    println (double(1))
    println (d2 (1))
    println (d2 (1))
    return
}

// EOF