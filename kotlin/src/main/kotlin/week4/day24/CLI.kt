package week4.day24

import util.withInput
import week3.day21.cli



fun Gate.dump () {
    println (this)
    println (" - inputs:  ${inputs}")
    println (" - outputs: ${outputs}")
}

fun main () {
    withInput(DAY, SAMPLE) { input ->
        val model = Model.parse(input)

        fun dump () {
            val x = model.x
            val y = model.y
            println (String.format ("x: %16s %45s", x, x.toString (2)))
            println (String.format ("y: %16s %45s", y, y.toString (2)))
            return
        }

        fun dump (wire: Wire?) {
            if (wire == null) {
                println (wire)
            } else {
                println("$wire ${wire.type}")
                println(" - I ${wire.input}")
                println(" - O ${wire.outputs}")
            }
        }

        cli ("24> ") { args ->
            if (args.size > 0) {
                when (args[0]) {
                    "w" -> {
                        val wire = model.wires[args[1]]
                        dump (wire)
                    }
                    "go" -> {
                        val wire = model.wires[args[1]]
                        val gates = model.gates.filter { wire in it.outputs }
                        if (gates.isEmpty()) {
                            println ("none")
                        } else {
                            gates.forEach {
                                it.dump ()
                            }
                        }
                    }
                    "gi" -> {
                            val wire = model.wires[args[1]]
                            val gates = model.gates.filter { wire in it.inputs }
                            if (gates.isEmpty()) {
                                println ("none")
                            } else {
                                gates.forEach {
                                    it.dump ()
                                }
                            }
                        }
                    "?" -> {
                        if (args.size == 1) {
                            dump ()
                        } else {
                            println ("USAGE: ${args[0]}")
                        }
                    }
                    "x?" -> {
                        if (args.size == 1) {
                            val x = model.x
                            println (x)
                            println (x.toString (2))
                        } else {
                            println ("USAGE: ${args[0]}")
                        }
                    }
                    "y?" -> {
                        if (args.size == 1) {
                            val y = model.y
                            println (y)
                            println (y.toString (2))
                        } else {
                            println ("USAGE: ${args[0]}")
                        }
                    }
                    "z?" -> {
                        if (args.size == 1) {
                            val z = model.z
                            println (z)
                            println (z.toString (2))
                        } else {
                            println ("USAGE: ${args[0]}")
                        }
                    }
                    "x" -> {
                        if (args.size == 2) {
                            model.x = args[1].toLong()
                        } else {
                            println ("USAGE: ${args[0]} <value>")
                        }
                    }
                    "y" -> {
                        if (args.size == 2) {
                            model.y = args[1].toLong()
                        } else {
                            println ("USAGE: ${args[0]} <value>")
                        }
                    }
                    "run" -> {
                        model.part1 ()
                        val z = model.z
                        println (String.format ("z: %16s %45s", z, z.toString (2)))
                    }
                    else -> println ("ERROR: Unrecognized command: '${args[0]}'")
                }
            }
            false
        }
    }
}

// EOF