package week3.day21

import util.interactive

fun sign (value: Int): Int {
    return when {
        value < 0 -> -1
        value > 0 -> 1
        else -> 0
    }
}

val <T> List<List<T>>.flatten: List<T>
    get () {
        val that = this
        return buildList {
            that.forEach { a ->
                a.forEach { b ->
                    add(b)
                }
            }
        }
    }

val List<String>.shortestStrings: List<String>
    get () {
        val min = map { it.length }.min()
        return filter { it.length == min }
    }

val <T> List<List<T>>.shortest: List<List<T>>
    get () {
        val min = map { it.size }.min ()
        return filter { it.size == min }
    }

fun <T> combinations (lists: List<List<List<T>>>): List<List<List<T>>> {
    if (lists.isEmpty ()) {
        return listOf (emptyList ())
    }

    val first = lists.first ()
    val rest = lists.subList (1, lists.size)

    return buildList {
        first.forEach { first ->
            val completions = combinations (rest)
            completions.forEach { completion ->
                add (buildList {
                    add (first)
                    addAll (completion)
                })
            }
        }
    }
}

fun cli (prompt: String = "> ", func: (List<String>) -> Boolean) {
    interactive (prompt) { input ->
        val args = input.split(Regex("\\s+")).filter { it.isNotEmpty() }
        func(args)
    }
    return
}

fun main () {
    val lists = listOf (
        listOf (
            listOf ('<', 'A'),
        ),
        listOf (
            listOf ('^', 'A'),
        ),
        listOf (
            listOf ('>', '^', '^', 'A'),
            listOf ('^', '>', '^', 'A'),
            listOf ('^', '^', '>', 'A'),
        ),
        listOf (
            listOf ('v', 'v', 'v', 'A')
        )
    )
    val combos = combinations (lists)
    println (combos)
    val flat = combos.map { it.flatten }
    println (flat)


}

// EOF