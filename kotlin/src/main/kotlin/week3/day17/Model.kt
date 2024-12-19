package week3.day17

import java.io.IOException
import java.util.regex.Pattern


data class Computer (
    val registers: MutableMap<Register, Long>,
    val program: MutableList<Long>
) {
    var iptr = 0
    val output = mutableListOf<Int> ()

    fun clear () {
        Register.entries.forEach {
            setRegister (it, 0)
        }
        iptr = 0
        output.clear ()
        program.clear ()
        return
    }

    fun emit (value: Int) {
        output.add (value)
        return
    }

    val fetch: Long
        get () = program [iptr++]

    fun setRegister (register: Register, value: Long) {
        registers [register] = value
        return
    }
    fun getRegister (register: Register): Long = registers [register] as Long

    fun run (): List<Int> {
        while (iptr < program.size) {
            step ()
        }
        return output
    }

    fun step () {
        if (iptr < program.size) {
            val op = Operator.parse (fetch)
            val operand = Operand.parse (fetch)
            op (this, operand)
        } else {
            throw IllegalStateException ("Halted.")
        }
        return
    }

    fun dumpRegisters () {
        registers.forEach { (register, value) ->
            print ("$register=$value | ")
        }
        println ("iptr=$iptr")
        return
    }

    private fun dumpRange (): IntRange {
        var a = iptr - 2
        var b = iptr + 4
        if (a < 0) {
            a += 2
            b += 2
        }
        if (b > program.size) {
            b = program.size
            if (a >= 4) {
                a -= 4
            } else if (a >= 2) {
                a -= 2
            }
        }
        return a until b
    }

    fun dump () {
        var ptr = 0
        dumpRegisters ()
        println ("output=$output")
        for (ptr in dumpRange () step 2) {
            val op = Operator.parse (program[ptr])
            val operand = Operand.parse (program[ptr + 1])
            println ("${if (iptr == ptr) ">" else " "} $ptr: $op $operand")
        }
        return
    }

    companion object {
        fun parse (input: String): Computer {
            val index = input.indexOf ("\n\n")
            val first = input.substring (0, index)
            val second = input.substring (index + 2, input.length)

            // Extract the registers

            val pattern = Pattern.compile ("Register (.): (\\d+)")
            val registers = buildMap {
                first.split ("\n").forEach { row ->
                    val matcher = pattern.matcher(row)
                    if (! matcher.matches ()) {
                        throw IOException ("Invalid input: $row")
                    }
                    put (Register.valueOf (matcher.group (1)), matcher.group (2).toLong ())
                }
            }.toMutableMap()

            // Extract the program

            val i = second.indexOf (": ")
            val program = second.substring (i + 2, second.length)
                .split (",")
                .map { it.toLong () }
                .toMutableList ()

            return Computer (registers, program)
        }
    }
}