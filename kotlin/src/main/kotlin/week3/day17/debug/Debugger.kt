package week3.day17.debug

import util.interactive
import util.repeat
import util.withInput
import week3.day17.*
import java.util.regex.Pattern

fun main () {
    println("day$DAY, part1 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        var computer = Computer.parse(input)
        computer.dump ()

        fun setRegister (which: Register, args: List<String>) {
            if (args.size != 2) {
                println ("USAGE: ${args[0]} <value>")
            } else {
                computer.setRegister (which, args[1].toLong ())
            }
            computer.dumpRegisters()
            return
        }

        interactive ("cpu> ") { cmd ->
            val args = cmd.split (Pattern.compile ("\\s+")).filter { it.isNotBlank() }
            if (args.isNotEmpty ()) {
                when (args[0]) {
                    "quit" -> return@interactive true
                    "reset" -> {
                        computer = Computer.parse(input)
                        computer.dump ()
                    }
                    "step" -> {
                        val count = if (args.size > 1) args[1].toInt () else 1
                        count.repeat {
                            computer.step ()
                        }
                        computer.dump ()
                    }
                    "run" -> {
                        val output = computer.run ()
                        computer.dump ()
                        println (output)
                    }
                    "a" -> setRegister (Register.A, args)
                    "b" -> setRegister (Register.B, args)
                    "c" -> setRegister (Register.C, args)
                    "iptr" -> {
                        if (args.size != 2) {
                            println ("USAGE :${args[0]} <addr>")
                        } else {
                            computer.iptr = args[1].toInt ()
                            computer.dumpRegisters()
                        }
                    }
                    "clear" -> {
                        computer.clear ()
                        computer.dump ()
                    }
                    "add" -> {
                        if (args.size % 2 != 1) {
                            println ("USAGE: ${args[0]} (<op> <operand>)+")
                        } else {
                            val codes = args.subList (1, args.size).map { it.toLong () }
                            computer.program.addAll (codes)
                            computer.dump ()
                        }
                    }
                    "program" -> {
                        if (args.size % 2 != 1) {
                            println ("USAGE: ${args[0]} (<op> <operand>)+")
                        } else {
                            println (computer.program)
                        }
                    }
                    "list" -> {
                        if (args.size != 1) {
                            println ("USAGE: ${args[0]}")
                        } else {
                            var i = 0
                            computer.program.let {
                                for (i in 0 until it.size step 2) {
                                    val op = Operator.parse (it[i])
                                    val operand = Operand.parse (it[i + 1])
                                    println ("$i: $op $operand")
                                }
                            }
                        }
                    }
                    else -> println ("ERROR: Unrecognized command: ${args[0]}")
                }
            }
            false
        }
    }
    return
}

// EOF