package week4.day24

import util.halt
import java.util.regex.Pattern

private fun wire (prefix: String): (Int) -> String = { i -> String.format ("$prefix%02d", i) }
val xWire = wire ("x")
val yWire = wire ("y")
val zWire = wire ("z")

class Model (val wires: Map<String, Wire>, val gates: List<Gate>) {
    val bits = wires.keys
        .filter { it.startsWith ("z") }
        .map { it.substring (1, it.length) }
        .map { it.toInt () }
        .max ()

    fun value (prefix: String): Long {
        var value = 0L
        for (bit in 0 until bits) {
            val wire = String.format ("$prefix%02d", bit)
            if (get (wire).value == true) {
                value += 1L shl bit
            }
        }
        return value
    }

    private fun value (prefix: String, value: Long) {
        for (bit in 0 until bits) {
            val wire = String.format ("$prefix%02d", bit)
            val isSet = value and (1L shl bit) != 0L
            wires[wire]!!.value = isSet
        }

    }

    var z: Long
        get () = value ("z")
        set (value: Long) {
            value ("z", value)
        }
    var x: Long
        get () = value ("x")
        set (value: Long) {
            value ("x", value)
        }
    var y: Long
        get () = value ("y")
        set (value: Long) {
            value ("y", value)
        }

    val output: Long
        get () {
            var value = 0L
            for (i in 0 until 64) {
                val name = String.format ("z%02d", i)
                val wire = wires[name]
                if (wire == null) {
                    break
                } else {
                    when (wire.value) {
                        false -> Unit
                        true -> value = value or (1L shl i)
                        null -> throw IllegalStateException ("Wire value is unset: ${wire.name}")
                    }
                }
            }
            return value
        }

    private fun checkReady (wires: List<Wire>): Boolean {
        return wires.all { it.value != null }
    }

    fun part1 () {
        val gates = buildList {
            addAll (gates)
        }.toMutableList ()
        while (gates.isNotEmpty ()) {
            val remove = mutableListOf<Gate> ()
            gates.forEach { gate ->
                if (checkReady (gate.inputs)) {
                    val inputs = gate.inputs.map {
                        it.value!!
                    }
                    val value = gate (inputs)
                    gate.outputs.forEach { output ->
                        output.value = value
                    }
                    remove.add (gate)
                }
            }
            if (remove.isEmpty ()) {
                throw IllegalStateException ()
            }
            gates.removeAll (remove)
        }
    }

    /**
     * Find the path from the specified input to the specified output.
     */

    fun path (from: String, to: String): List<Gate> {
        var wire = wires[from] as Wire
        return buildList {

        }
    }

    fun clear () {
        for (bit in 0 until bits) {
            set (yWire (bit), false)
            set (xWire (bit), false)
        }
    }
    fun set (wire: String, value: Boolean) {
        wires[wire]!!.value = value
    }
    fun get (wire: String) = wires[wire] as Wire

    fun findGate (outputWire: String): Gate {
        val wire = wires [outputWire] as Wire
        val gates = gates.filter { wire in it.outputs }
        return if (gates.size == 1) {
            gates[0]
        } else {
            halt ()
        }
    }

    companion object {
        fun parse (input: String): Model {
            val iter = input.split ("\n").iterator ()
            val wires = mutableMapOf<String, Wire> ()

            // Parse the inputs

            while (true) {
                val str = iter.next ()
                if (str.isEmpty ()) {
                    break
                }
                val (name, value) = str.split (": ")
                wires[name] = Wire (name,null, value == "1")
            }

            // Now parse the gates

            val gates = mutableListOf<Gate> ()
            val pattern = Pattern.compile ("(\\w+) (AND|OR|XOR) (\\w+) -> (\\w+)")
            while (iter.hasNext ()) {
                val str = iter.next ()
                val matcher = pattern.matcher (str)
                if (!matcher.matches()) {
                    throw IllegalStateException ("Invalid input: $str")
                }
                val in1 = matcher.group (1)
                val op = matcher.group (2)
                val in2 = matcher.group (3)
                val out = matcher.group (4)

                val config = Configuration (listOf (in1, in2), listOf (out))
                gates += Gate.new (op, config)
            }

            // Create all the missing wires that are implied by the gate wiring

            gates.forEach { gate ->
                gate.config.outputs.forEach { output ->
                    if (output !in wires) {
                        val wire = Wire(output, gate)
                        wires[output] = wire
                    }
                    gate.outputs += wires[output] as Wire
                }
            }

            // Now we can wire up the gates

            gates.forEach { gate ->
                gate.config.inputs.forEach { input ->
                    val wire = wires[input] as Wire
                    gate.inputs += wire
                    wire.outputs.add (gate)
                }
            }

            return Model (wires, gates)
        }
    }
}

// EOF