package day11

import util.interactive

fun main () {
    interactive ("day11> ") { str ->
        val args = parseArgs (str)
        if (args.size != 2) {
            println ("USAGE: <start> <reps>")
        } else {
            val model = Model ("${args[0]}")
            println (model)
            for (i in 1 .. args[1].toInt()) {
                model.tick ()
                println (model)
            }
        }
        false
    }
    return
}

fun parseArgs  (str: String) = str.split(" ").filter { it.isNotEmpty () }

// EOF