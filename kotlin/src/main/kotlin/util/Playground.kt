package util

fun main () {
    val a = with (1) {
        this + 1
    }
    println (a)


//    val list1 = listOf ("a" to 1, "b" to 2, "c" to 3)
//    println (list1.flatMap { listOf (it.first, it.second) })
//
//    val list2 = listOf ("123", "456")
//    println (list2.flatMap { it.toList () })
//
//    val list3 = listOf (list1, list2)
//    println (list3.flatten ())

    run {
        val input = "a-b\nc-d\ne-f\na-c"
        val els = input.split ("\n")
            .map { it.split ("-") }
            .flatMap { (first, second) -> listOf (first to second, second to first) }
            .groupBy ({ it.first }, { it.second })
            .mapValues { it.value.toSet () }
        println (els)
    }

    return
}

// EOF