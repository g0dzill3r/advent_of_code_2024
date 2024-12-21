package week3.day21

import util.withInput


val DAY = 21
val SAMPLE = true

fun main () {
    println("day$DAY, part1 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val codes = Code.parseCodes (input)

        val kpad = NumericKeypad ()
        val dpad = DirectionalKeypad ()
        var total = 0

        codes.forEach { code ->

            // figure out the sequence of operations to type the code on the numeric keypad

            println(code)
            kpad.reset()
            kpad.press(code.code)
            val first = buildSet {
                combinations(kpad.accum)
                    .map { it.flatten }
                    .forEach {
                        add (it.encoded)
                    }
            }.toList ().shortestStrings

            // Now we figure out what it would take to generate this sequence using the first directional keypad

            val second = buildSet {
                first.forEach { code ->
                    dpad.reset()
                    dpad.press(code)
                    combinations(dpad.accum)
                        .map { it.flatten }
                        .forEach {
                            add(it.encoded)
                        }
                }
            }.toList().shortestStrings

            // Now we need to run it through a second level directional keypad

            val third = buildSet {
                second.forEach { code ->
                    dpad.reset()
                    dpad.press(code)
                    combinations(dpad.accum)
                        .map { it.flatten }
                        .forEach {
                            add(it.encoded)
                        }
                }
            }.toList().shortestStrings

            total += code.numeric * third[0].length
        }
        println (total)
    }
    return
}

// EOF