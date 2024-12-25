package week4.day24

import util.halt

class Op (val op: String, val left: Any, val right: Any) {
    override fun toString () = "$op($left,$right)"
}

fun half (i: Int): Op = Op ("XOR", xWire(i), yWire(i))

fun carry (i: Int): Op {
    return when (i) {
        0 -> halt ()
        1 -> Op ("AND", xWire(i-1), yWire(i-1))
        else -> {
            val a = Op ("AND", xWire(i-1), yWire(i-1))
            val x = Op ("XOR", xWire(i-1), yWire(i-1))
            val c = carry (i - 1)
            return Op ("OR", Op ("AND", c, x), a)
        }
    }
}

fun ideal (i: Int): Op {
    return when (i) {
        0 -> half (i)
        1 -> Op ("XOR", carry (i), half (i))
        else -> Op ("XOR", carry (i), half (i))
    }
}

fun main () {
    println (carry (1))
    println (carry (2))

    println (ideal (0))
    println (ideal (1))
    println (ideal (2))
}

/*
XOR(x00,y00)
XOR(x00,y00)


XOR(AND(x00,y00),XOR(x01,y01))
XOR(AND(x00,y00),XOR(x01,y01))


XOR(OR(AND(AND(x00,y00),XOR(x01,y01)),AND(x01,y01)),XOR(x02,y02))
XOR(OR(AND(AND(x00,y00),XOR(x01,y01)),AND(x01,y01)),XOR(x02,y02))


XOR(OR(AND(OR(AND(AND(x00,y00),XOR(x01,y01)),AND(x01,y01)),XOR(x02,y02)),AND(x02,y02)),XOR(x03,y03))
XOR(OR(AND(OR(AND(AND(x00,y00),XOR(x01,y01)),AND(x01,y01)),XOR(x02,y02)),AND(x02,y02)),XOR(x03,y03))



 */

// EOF