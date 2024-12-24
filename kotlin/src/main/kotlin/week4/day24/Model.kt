package week4.day24

import java.util.regex.Pattern

class Model (val wires: Map<String, Wire>, val gates: List<Gate>) {
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