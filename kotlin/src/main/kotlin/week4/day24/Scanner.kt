package week4.day24

import util.withInput


fun main () {
    println("day$DAY, part2 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val model = Model.parse(input)
        val bits = model.bits

        // We'll keep track of the carry outputs as we go along

        val carry = mutableListOf<Gate> ()

        // Let's look through the circuit looking for deviations from a standard
        // full adder circuit for each bit

        for (bit in 0 until bits) {
            println ("BIT $bit")
            val prefix = "x${bit}/y${bit}"

            val x = model.get (xWire (bit))
            val y = model.get (yWire (bit))

            val xo = x.outputs
            val yo = y.outputs

            // Make sure the x0 and y0 outputs match

            if (xo != yo) {
                println ("$prefix: x and y outputs don't match")
                continue
            } else if (xo.size != 2) {
                println ("$prefix: output wrong size")
                continue
            }

            // Get the XOR gate leading to the z output

            do {
                val xor = xo.first { it is Gate.XOR }


                val wire = xor.outputs[0]
                println (wire)
                if (bit == 0) {
                    if (wire.name != zWire (0)) {
                        println ("$prefix -> XOR goes to the wrong place (${wire.name})")
                    }
                } else {
                    val next = wire.outputs.filter { it is Gate.XOR }
                    if (next.size != 1) {
                        println ("$prefix -> XOR -> XOR not found (${wire.name})")
                    } else if (next[0].outputs[0].name != zWire (bit)) {
                        println ("$prefix -> XOR -> XOR points to z(${next[0].outputs[0].name}) (${next[0].outputs[0].name})")
                    }
                }
            } while (false)


            // Now lets look at the carry circuitry

//            val and = xo.first { it is Gate.AND }
//            if (x !in and.inputs || y !in and.inputs) {
//                println ("x/y($bit) --> AND has wrong inputs")
//            }
//
//            // There is no carry for the first bit so it goes directly to the XOR and onto the output
//
//            if (bit == 0) {
//                if (and.outputs.size != 1) {
//                    println ("$prefix -> AND has excess outputs")
//                } else if (and.outputs[0].outputs[0] !is Gate.XOR) {
//                    println ("$prefix -> AND -> XOR expected; found ${and.outputs[0].outputs[0]}")
//                } else if (and.outputs[0].outputs[0].outputs[0] != model.get (zWire (bit))) {
//                    println ("$prefix -> AND -> XOR points to wrong output")
//                }
//                // Save the carry gate for bit 1
//                carry.add (and)
//            } else {

                // look for the first and gate

//                if (and.inputs != listOf (x, y)) {
//
//                }
//                println ("   AND ${and.inputs}")
//                println ("     outputs=${and.outputs}")
//                println ("     next=${and.outputs[0].outputs}")
//                println ("     output=${and.outputs[0].outputs[0].outputs}")
//                println ("     output=${and.outputs[0].outputs[0].outputs[0].outputs[0]}")
//
//                if (and.outputs.size != 1) {
//                    println ("x/y($bit) -> AND --> ? more than 1")
//                    continue
//                }
//                val wire = model.get (and.outputs[0].name)
//                println ("wire is ${wire.name}")
//                if (wire.outputs.size != 1) {
//                    println ("x/y($bit) --> AND --> wire has != 1 outputs (${wire.outputs.size}) (${wire.name})")
//                    continue // MAYBE NOT
//                } else if (wire.outputs[0] !is Gate.OR) {
//                    println ("x/y($bit) --> AND --> OR missing (${wire.name})")
//                    continue
//                }
//
//                // trace it to the or gate and make sure the or gate proceeds to the output
//
//                val or = wire.outputs[0]
//                if (or.outputs.size != 1) {
//                    println ("x/y($bit) --> OR has != 1 outputs")
//                } else {
//                    val wire = or.outputs[0]
//                    println (wire.outputs)
//                }

                // save the org gate as the carry for the next bit

                // look back at the other and gate

                // trace the or gate to the


//            }
        }

        val found = listOf ("thm", "wrm", "hwq", "gbs")
        println (found.sorted ().joinToString (","))
    }
    return
}

// EOF