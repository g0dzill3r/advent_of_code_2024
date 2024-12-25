package week4.day24

import util.halt
import util.withInput


fun main () {
    println("day$DAY, part2 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val model = Model.parse(input)

        // Get a description of each output

        fun describeWire (name: String): String {
            val wire = model.wires[name] as Wire
            return if (wire.isInput) {
                name
            } else {
                val gate = model.findGate (name)
                buildString {
                    append (gate::class.java.simpleName)
                    append ('(')
                    val sorted = gate.inputs.sortedBy { it::class.java.simpleName }.map { describeWire (it.name) }.sorted ()
                    sorted.forEachIndexed { i, el ->
                        if (i != 0) {
                            append (',')
                        }
                        append (el)
                    }
                    append (')')
                }
            }
        }

        for (i in 0 until model.bits) {
            val wire = String.format("z%02d", i)
            val actual = describeWire(wire)
            println ("$i: $actual")
            val ideal = ideal(i).toString()
            println("$i: ${actual == ideal}")
            if (actual != ideal) {
                println(actual)
                println(ideal)
            }
        }


    }
    return
}

// EOF