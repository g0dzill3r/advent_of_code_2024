package week3.day17

import util.repeat
import util.withInput

fun main () {
    println("day$DAY, part1 ${if (SAMPLE) "(SAMPLE)" else "}"}")
    withInput(DAY, SAMPLE) { input ->
        val computer = Computer.parse (input)
        computer.dump ()

        // Let's find the number that at least generates a result of the proper length

        val match = computer.program
        val len = match.size
        println ("program $match")

        val result = computer.run ()
        println (result)


        var i = 1L
        while (true) {
            val value = 10L.pow (i)
            val computer = Computer.parse (input)
            computer.setRegister (Register.A, value)
            val results = computer.run ()
            println ("$i: $results")
            if (results.size == len) {
                println ("USE $i")
                break
            }
            i ++
        }

        // Now we know our starting point

        var start = 10L.pow (i)
        println ("STARTING AT $start")
        computer.iptr = 0
        computer.output.clear ()
        computer.setRegister (Register.A, start)
        val results = computer.run ()
        println (results)

        // Now figure out the direction

        val direction = computer.program[0] - results[0]
        if (direction == 0L) {
            throw Exception ("Unhandled.")
        }

        println ("====")
        println (computer.program)

        var j = 0
        var found: Long? = null

        while (true) {
            computer.iptr = 0
            computer.output.clear ()
            val value = start + j * direction
            computer.setRegister (Register.A, value)
            val results = computer.run ()
            if (results.size < match.size) {
                println ("OVERRAN")
            }
            if (results == match) {
                found = value
                println ("FOUND: $value")
                break
            }
        }
    }

    return
}

// EOF

