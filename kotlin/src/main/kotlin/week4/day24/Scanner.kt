package week4.day24

import util.withInput


fun main () {
    println("day$DAY, part2 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val model = Model.parse(input)
        val bits = model.bits

        // Let's look through the circuit looking for deviations from a standard
        // full adder circuit for each bit

        for (bit in 0 until bits) {
            println ("BIT $bit")
            val x = model.get (xWire (bit))
            val y = model.get (yWire (bit))

            val xo = x.outputs
            val yo = y.outputs
            if (xo != yo) {
                println ("x and y outputs don't match")
            } else if (xo.size != 2) {
                println ("output wrong size")
            }
            val xor = xo.first { it is Gate.XOR }
            xor.outputs.let {
                if (it.size != 1) {
                    println ("xor wrong size")
                } else {
                    if (bit == 0) {
                        if (it[0].name != zWire (0)) {
                            println ("x0/y0 goes to the wrong place")
                        }
                    } else {
                        val next = it[0].outputs.filter { it is Gate.XOR }
                        if (next.size != 1) {
                            println ("x/y -> XOR -> XOR not found")
                        } else if (next[0].outputs[0].name != zWire (bit)) {
                            println ("x/y($bit) -> XOR -> XOR points to z(${next[0].outputs[0].name})")
                        }
                    }
                }
            }

            val and = xo.first { it is Gate.AND }
            if (x !in and.inputs || y !in and.inputs) {
                println ("x/y($bit) --> AND has wrong inputs")
            }
            println (and)
            if (bit == 0) {
                if (and.outputs.size != 1 || and.outputs[0].outputs[0] !is Gate.XOR) {
                    println ("x/y($bit) --> AND --> XOR expected; found ${and.outputs[0].outputs[0]}")
                }
            } else {

            }
        }
    }
    return
}

// EOF