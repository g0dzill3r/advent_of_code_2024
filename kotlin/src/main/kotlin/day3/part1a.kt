package day3

import util.InputUtil
import util.interactive

/**
 * A poor-mans regex approach.
 */

fun main () {
    val input = InputUtil.getInput(DAY, true)

    val matcher = Matcher();
    val matches = mutableListOf<String> ()
    matcher.addPattern("mul([0123456789]?[0123456789]?[0123456789],[0123456789]?[0123456789]?[0123456789])") { i, str -> matches.add (str) }
    matcher.match (input)

    var total = 0
    val submatcher = Matcher ()
    var l = 0
    var r = 0
//    submatcher.addPattern ("("
//    matches.forEach {
//        val
//    }

    println (matches)
    println (total)
    return
}

// EOF