package week3.day17.part2

import week3.day17.pow

fun main () {
    fun split(i: Int): List<Int> {
        return buildList {
            var j = i
            while(j != 0) {
                j /= 8
                add (j % 8)
            }
        }
    }

    val target = listOf (0, 3, 5, 4, 3, 0)

    val a = 2131
    val den = 8
    val pow = 1
    val aim = 2

    var r = 0
    r += 3*8.pow(5) + 4*8.pow (4) + 5*8.pow (3) + 3*8.pow (2)  + 0*8.pow (1)


    println (r)
    println (split (r))


//    var rs = (aim * den.pow (pow)) .. (aim * den.pow (pow) + (den - 1))
//    println (rs)
//    for (r in rs) {
//        println(split(r))
//    }

//    println (split (117440))
//    var i = 0
//    while (true) {
//        if (split (i) 0 ) {
//            break
//        }
//        i ++
//    }
//    println (i)
    return
}