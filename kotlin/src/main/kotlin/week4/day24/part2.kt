package week4.day24

import util.halt
import util.withInput

fun main () {
    println("day$DAY, part2 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val model = Model.parse(input)

        // Let's see where in the login the errors occur


        for (bit in 0 until model.bits) {
            println ("BIT $bit")
            val x = xWire (bit)
            val y = yWire (bit)

            fun run (desired: Long) {
                model.part1()
                val actual = model.output
                println (" - $x(${model.get(x).value}) + $y(${model.get(y).value}) -> $desired ${if (desired == actual) "==" else "!="} $actual actual")
                return
            }

            model.clear ()
            model.set (x, true)
            model.set (y, false)
            run (1L shl bit)

            model.clear ()
            model.set (x, false)
            model.set (y, true)
            run (1L shl bit)

            model.clear ()
            model.set (x, true)
            model.set (y, true)
            run (1L shl (bit + 1))

//            println (model.output)
        }


        // Get a description of each output

        fun findGate (outputWire: String): Gate {
            val wire = model.wires [outputWire] as Wire
            val gates = model.gates.filter { wire in it.outputs }
            return if (gates.size == 1) {
                gates[0]
            } else {
                halt ()
            }
        }

        fun describeWire (name: String): String {
            val wire = model.wires[name] as Wire
            return if (wire.isInput) {
                name
            } else {
                val gate = findGate (name)
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
    }
    return
}

// EOF