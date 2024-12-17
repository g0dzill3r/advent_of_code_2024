package week3.day17.test

import week3.day17.Computer
import week3.day17.Register

data class RegisterState (
    val a: Int? = null,
    val b: Int? = null,
    val c: Int? = null
) {
    val map: MutableMap<Register, Int>
        get () = buildMap {
            put (Register.A, a ?: 0)
            put (Register.B, b ?: 0)
            put (Register.C, c ?: 0)
        }.toMutableMap ()
}

data class InputState (
    val registers: RegisterState? = null,
    val program: List<Int>
)

data class OutputState (
    val registers: RegisterState? = null,
    val output: List<Int>? = null,
    val iptr: Int? = null
)

data class UnitTest (
    val init: InputState,
    val end: OutputState,
    val desc: String? = null
)

/**
 * Run a unit test.
 */

private fun runTest (test: UnitTest): Boolean {
    val registers = test.init.registers ?: RegisterState ()
    val computer = Computer (registers.map, test.init.program.toMutableList ())
    val output = computer.run ()
    var success = true

    test.end.output?.let { a ->
        if (a != output) {
            println ("ERROR: ${test.desc}: Output mismatch: $a != $output")
            success = false
        }
    }
    test.end.registers?.let { a ->
        if (a.c != null && a.c != computer.getRegister (Register.C)) {
            println ("ERROR: ${test.desc}: Invalid register C result: ${a.c} != ${computer.getRegister (Register.C)}")
            success = false
        }
        if (a.b != null && a.b != computer.getRegister (Register.B)) {
            println ("ERROR: ${test.desc}: Invalid register B result: ${a.b} != ${computer.getRegister (Register.B)}")
            success = false
        }
        if (a.a != null && a.a != computer.getRegister (Register.A)) {
            println ("ERROR: ${test.desc}: Invalid register A result: ${a.a} != ${computer.getRegister (Register.A)}")
            success = false
        }
    }
    test.end.iptr?.let { a ->
        if (a != computer.iptr) {
            println ("ERROR: ${test.desc}: Invalid iptr: ${computer.iptr} != $a")
            success = false
        }
    }
    return success
}

fun main () {
    var success = 0
    for (test in unitTests) {
        if (runTest (test)) {
            success++
        }
    }
    println ("Ran ${unitTests.size} tests.")
    println ("${unitTests.size - success} tests failed.")
    return
}

// EOF