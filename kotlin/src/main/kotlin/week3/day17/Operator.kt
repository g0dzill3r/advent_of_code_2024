package week3.day17

abstract  class Operator (val op: String) {
    abstract operator fun invoke (computer: Computer, operand: Operand)

    //    The adv instruction (opcode 0) performs division. The numerator is the value in the A register.
    //    The denominator is found by raising 2 to the power of the instruction's combo operand. 
    //    (So, an operand of 2 would divide A by 4 (2^2); an operand of 5 would divide A by 2^B.) 
    //    The result of the division operation is truncated to an integer and then written to the A register.

     class ADV : Operator ("adv") {
        override operator fun invoke (computer: Computer, operand: Operand) {
            val num = computer.getRegister (Register.A)
            val den = 2.pow (operand.comboValue (computer))
            computer.setRegister(Register.A, num / den)
            return
        }
    }
    //The bxl instruction (opcode 1) calculates the bitwise XOR of register B and the instruction's 
    // literal operand, then stores the result in register B.

     class BXL : Operator ("bxl") {
        override operator fun invoke (computer: Computer, operand: Operand) {
            val b = computer.getRegister (Register.B)
            val value = operand.literalValue
            computer.setRegister(Register.B, b xor value)
            return
        }
    }

    //The bst instruction (opcode 2) calculates the value of its combo operand modulo 8 
    // (thereby keeping only its lowest 3 bits), then writes that value to the B register.

     class BST : Operator ("bst") {
        override operator fun invoke (computer: Computer, operand: Operand) {
            val value = operand.comboValue (computer) % 8
            computer.setRegister (Register.B, value)
            return
        }
    }

    //The jnz instruction (opcode 3) does nothing if the A register is 0. However, if the A register
    // is not zero, it jumps by setting the instruction pointer to the value of its literal operand; if this instruction jumps,
    // the instruction pointer is not increased by 2 after this instruction.

     class JNZ : Operator ("jnz") {
        override operator fun invoke (computer: Computer, operand: Operand) {
            if (computer.getRegister (Register.A) != 0) {
                computer.iptr = operand.literalValue.toInt ()
            }
            return
        }
    }

    //The bxc instruction (opcode 4) calculates the bitwise XOR of register B and register C, 
    // then stores the result in register B. (For legacy reasons, this instruction reads an operand but ignores it.)

     class BCX : Operator ("bcx") {
        override operator fun invoke (computer: Computer, operand: Operand) {
            val b = computer.getRegister (Register.B)
            val c = computer.getRegister (Register.C)
            computer.setRegister (Register.B, b xor c)
            return
        }
    }

    //The out instruction (opcode 5) calculates the value of its combo operand modulo 8,
    // then outputs that value. (If a program outputs multiple values, they are separated by commas.)

     class OUT : Operator ("out") {
        override operator fun invoke (computer: Computer, operand: Operand) {
            val value = operand.comboValue (computer) % 8
            computer.emit (value)
            return
        }
    }

    //The bdv instruction (opcode 6) works exactly like the adv instruction except that the 
    // result is stored in the B register. (The numerator is still read from the A register.)

     class BDV : Operator ("bdv") {
        override operator fun invoke (computer: Computer, operand: Operand) {
            val num = computer.getRegister (Register.A)
            val den = 2.pow (operand.comboValue (computer))
            computer.setRegister(Register.B, num / den)
            return
        }
    }

    //The cdv instruction (opcode 7) works exactly like the adv instruction except that the 
    // result is stored in the C register. (The numerator is still read from the A register.)
     class CDV : Operator ("cdv") {
        override operator fun invoke (computer: Computer, operand: Operand) {
            val num = computer.getRegister (Register.A)
            val den = 2.pow (operand.comboValue (computer))
            computer.setRegister(Register.C, num / den)
            return
        }
    }

    override fun toString () = op

    companion object {
        fun parse (input: Int): Operator {
            return when (input) {
                0 -> ADV ()
                1 -> BXL()
                2 -> BST ()
                3 -> JNZ ()
                4 -> BCX ()
                5 -> OUT ()
                6 -> BDV ()
                7 -> CDV ()
                else -> throw IllegalStateException ("Illegal operator: $input")
            }
        }
    }
}

// EOF