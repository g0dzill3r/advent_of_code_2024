package week3.day17.test

val unitTests = listOf (
    // If register C contains 9, the program 2,6 would set register B to 1.
    UnitTest (
        desc="sample 1",
        init= InputState(
            registers= RegisterState(c=9),
            program=listOf (2, 6)
        ),
        end= OutputState (
            registers= RegisterState(b=1)
        )
    ),
    // If register A contains 10, the program 5,0,5,1,5,4 would output 0,1,2.
    UnitTest (
        desc="sample 2",
        init= InputState(
            registers= RegisterState(a=10),
            program=listOf (5,0,5,1,5,4)
        ),
        end= OutputState (
            output=listOf (0,1,2)
        )
    ),
    // If register A contains 2024, the program 0,1,5,4,3,0 would output 4,2,5,6,7,7,7,7,3,1,0 and leave 0 in register A.
    UnitTest (
        desc="sample 3",
        init= InputState (
            registers= RegisterState (a=2024),
            program=listOf(0,1,5,4,3,0)
        ),
        end= OutputState (
            registers= RegisterState (a=0),
            output=listOf (4,2,5,6,7,7,7,7,3,1,0)
        )
    ),
    // If register B contains 29, the program 1,7 would set register B to 26.
    UnitTest (
        desc="sample 4",
        init= InputState (
            registers= RegisterState (b=29),
            program=listOf (1,7)
        ),
        end= OutputState (
            registers= RegisterState (b=26),
        )
    ),
    // If register B contains 2024 and register C contains 43690, the program 4,0 would set register B to 44354.
    UnitTest (
        desc="sample 5",
        init= InputState (
            registers= RegisterState (b=2024, c=43690),
            program=listOf (4,0)
        ),
        end= OutputState (
            registers= RegisterState (b=44354),
        )
    ),
    // sample example
    UnitTest (
        desc="sample 6",
        init= InputState (
            registers= RegisterState (a=729),
            program=listOf (0,1,5,4,3,0)
        ),
        end= OutputState (
            registers= RegisterState (),
            output=listOf (4,6,3,5,6,3,5,2,1,0)
        )
    ),
    /*
    The adv instruction (opcode 0) performs division. The numerator is the value in the A register.
    The denominator is found by raising 2 to the power of the instruction's combo operand.
     (So, an operand of 2 would divide A by 4 (2^2); an operand of 5 would divide A by 2^B.)
     The result of the division operation is truncated to an integer and then written to the A register.
    */
    UnitTest (
        desc="adv L1",
        init=InputState(
            registers=RegisterState (a=16),
            program=listOf (0, 1)
        ),
        end=OutputState(
            registers=RegisterState (a=8)
        )
    ),
    UnitTest (
        desc="adv L2",
        init=InputState(
            registers=RegisterState (a=16),
            program=listOf (0, 2)
        ),
        end=OutputState(
            registers=RegisterState (a=4)
        )
    ),
    UnitTest (
        desc="adv L3",
        init=InputState(
            registers=RegisterState (a=16),
            program=listOf (0, 3)
        ),
        end=OutputState(
            registers=RegisterState (a=2)
        )
    ),
    UnitTest (
        desc="adv #a 1",
        init=InputState(
            registers=RegisterState (a=0),
            program=listOf (0, 4)
        ),
        end=OutputState(
            registers=RegisterState (a=0)
        )
    ),
    UnitTest (
        desc="adv #a 2",
        init=InputState(
            registers=RegisterState (a=1),
            program=listOf (0, 4)
        ),
        end=OutputState(
            registers=RegisterState (a=0)
        )
    ),
    UnitTest (
        desc="adv #b 1",
        init=InputState(
            registers=RegisterState (a=32, b=1),
            program=listOf (0, 5)
        ),
        end=OutputState(
            registers=RegisterState (a=16)
        )
    ),
    UnitTest (
        desc="adv #b 2",
        init=InputState(
            registers=RegisterState (a=32, b=3),
            program=listOf (0, 5)
        ),
        end=OutputState(
            registers=RegisterState (a=4)
        )
    ),
    UnitTest (
        desc="adv #c 1",
        init=InputState(
            registers=RegisterState (a=32, c=1),
            program=listOf (0, 6)
        ),
        end=OutputState(
            registers=RegisterState (a=16)
        )
    ),
    UnitTest (
        desc="adv #c 2",
        init=InputState(
            registers=RegisterState (a=32, c=3),
            program=listOf (0, 6)
        ),
        end=OutputState(
            registers=RegisterState (a=4)
        )
    ),
    /*
    The bxl instruction (opcode 1) calculates the bitwise XOR of register B and the instruction's
    literal operand, then stores the result in register B.
    */
    UnitTest (
        desc="bxl 1",
        init= InputState (
            registers=RegisterState(b=3),
            program=listOf (1, 0)
        ),
        end=OutputState (
            registers=RegisterState(b=3 xor 0)
        )
    ),
    UnitTest (
        desc="bxl 2",
        init= InputState (
            registers=RegisterState(b=3),
            program=listOf (1, 1)
        ),
        end=OutputState (
            registers=RegisterState(b=3 xor 1)
        )
    ),
    UnitTest (
        desc="bxl 3",
        init= InputState (
            registers=RegisterState(b=3),
            program=listOf (1, 2)
        ),
        end=OutputState (
            registers=RegisterState(b=3 xor 2)
        )
    ),
    UnitTest (
        desc="bxl 4",
        init= InputState (
            registers=RegisterState(b=3),
            program=listOf (1, 3)
        ),
        end=OutputState (
            registers=RegisterState(b=3 xor 3)
        )
    ),
    UnitTest (
        desc="bxl 5",
        init= InputState (
            registers=RegisterState(b=3),
            program=listOf (1, 4)
        ),
        end=OutputState (
            registers=RegisterState(b=3 xor 4)
        )
    ),
    UnitTest (
        desc="bxl 6",
        init= InputState (
            registers=RegisterState(b=3),
            program=listOf (1, 5)
        ),
        end=OutputState (
            registers=RegisterState(b=3 xor 5)
        )
    ),
    UnitTest (
        desc="bxl 7",
        init= InputState (
            registers=RegisterState(b=3),
            program=listOf (1, 6)
        ),
        end=OutputState (
            registers=RegisterState(b=3 xor 6)
        )
    ),
    UnitTest (
        desc="bxl 8",
        init= InputState (
            registers=RegisterState(b=3),
            program=listOf (1, 7)
        ),
        end=OutputState (
            registers=RegisterState(b=3 xor 7)
        )
    ),
    /*
    The bst instruction (opcode 2) calculates the value of its combo operand modulo 8 (thereby
    keeping only its lowest 3 bits), then writes that value to the B register.
    */
    UnitTest (
        desc="bst 1",
        init= InputState (
            program=listOf (2, 0)
        ),
        end=OutputState (
            registers=RegisterState(b=0)
        )
    ),
    UnitTest (
        desc="bst 2",
        init= InputState (
            program=listOf (2, 1)
        ),
        end=OutputState (
            registers=RegisterState(b=1)
        )
    ),
    UnitTest (
        desc="bst 3",
        init= InputState (
            program=listOf (2, 2)
        ),
        end=OutputState (
            registers=RegisterState(b=2)
        )
    ),
    UnitTest (
        desc="bst 4",
        init= InputState (
            program=listOf (2, 3)
        ),
        end=OutputState (
            registers=RegisterState(b=3)
        )
    ),
    UnitTest (
        desc="bst 5",
        init= InputState (
            registers=RegisterState(a=37),
            program=listOf (2, 4)
        ),
        end=OutputState (
            registers=RegisterState(b=5)
        )
    ),
    UnitTest (
        desc="bst 6",
        init= InputState (
            registers=RegisterState(b=38),
            program=listOf (2, 5)
        ),
        end=OutputState (
            registers=RegisterState(b=6)
        )
    ),
    UnitTest (
        desc="bst 7",
        init= InputState (
            registers=RegisterState(c=39),
            program=listOf (2, 6)
        ),
        end=OutputState (
            registers=RegisterState(b=7)
        )
    ),
    /*
    The jnz instruction (opcode 3) does nothing if the A register is 0. However, if the A
    register is not zero, it jumps by setting the instruction pointer to the value of its literal operand;
    if this instruction jumps, the instruction pointer is not increased by 2 after this instruction.
    */
    UnitTest (
        init= InputState (
            program=listOf ()
        ),
        end= OutputState (
            iptr=0
        )
    ),
    UnitTest (
        init= InputState (
            program=listOf (5,0,5,0)
        ),
        end= OutputState (
            iptr=4
        )
    ),
    UnitTest (
        init= InputState (
            program=listOf (5,0,5,0,3,2)
        ),
        end= OutputState (
            iptr=6
        )
    ),
//    UnitTest (
//        init= InputState (
//            registers=RegisterState (a=1),
//            program=listOf (5,0,5,0,3,2)
//        ),
//        end= OutputState (
//            iptr=2
//        )
//    ),
    /*
    The bxc instruction (opcode 4) calculates the bitwise XOR of register B and register C,
    then stores the result in register B. (For legacy reasons, this instruction reads an operand but ignores it.)
    */
    UnitTest (
        desc="bxc 1",
        init=InputState(
            registers=RegisterState (b=12, c=34),
            program=listOf (4, 0)
        ),
        end=OutputState(
            registers=RegisterState (b=12 xor 34, c=34)
        )
    ),
    UnitTest (
        desc="bxc 2",
        init=InputState(
            registers=RegisterState (b=2, c=-34),
            program=listOf (4, 0)
        ),
        end=OutputState(
            registers=RegisterState (b=2 xor -34, c=-34)
        )
    ),
    UnitTest (
        desc="bxc 3",
        init=InputState(
            registers=RegisterState (b=3, c=0),
            program=listOf (4, 0)
        ),
        end=OutputState(
            registers=RegisterState (b =3 xor 0, c=0)
        )
    ),
    /*
    The out instruction (opcode 5) calculates the value of its combo operand modulo 8,
    then outputs that value. (If a program outputs multiple values, they are separated by commas.)
    */
    UnitTest (
        desc="out 1",
        init= InputState (
            program=listOf (5, 0)
        ),
        end= OutputState (
            output=listOf (0)
        )
    ),
    UnitTest (
        desc="out 2",
        init= InputState (
            program=listOf (5, 1)
        ),
        end= OutputState (
            output=listOf (1)
        )
    ),
    UnitTest (
        desc="out 2",
        init= InputState (
            program=listOf (5, 2)
        ),
        end= OutputState (
            output=listOf (2)
        )
    ),
    UnitTest (
        desc="out 3",
        init= InputState (
            program=listOf (5, 3)
        ),
        end= OutputState (
            output=listOf (3)
        )
    ),
    UnitTest (
        desc="out a",
        init= InputState (
            registers=RegisterState (a=44),
            program=listOf (5, 4)
        ),
        end= OutputState (
            output=listOf (44 % 8)
        )
    ),
    UnitTest (
        desc="out b",
        init= InputState (
            registers=RegisterState (b=55),
            program=listOf (5, 5)
        ),
        end= OutputState (
            output=listOf (55 % 8)
        )
    ),
    UnitTest (
        desc="out c",
        init= InputState (
            registers=RegisterState (c=66),
            program=listOf (5, 6)
        ),
        end= OutputState (
            output=listOf (66 % 8)
        )
    ),
    /*
    The bdv instruction (opcode 6) works exactly like the adv instruction except that the
    result is stored in the B register. (The numerator is still read from the A register.)
    */
    UnitTest (
        desc="bdv 0/2",
        init= InputState (
            registers=null,
            program=listOf (6, 1)
        ),
        end= OutputState (
            registers=RegisterState (b=0)
        )
    ),
    UnitTest (
        desc="bdv 2/2",
        init= InputState (
            registers=RegisterState(a=2),
            program=listOf (6, 1)
        ),
        end= OutputState (
            registers=RegisterState (b=1)
        )
    ),
    UnitTest (
        desc="bdv 4/2",
        init= InputState (
            registers=RegisterState(a=4),
            program=listOf (6, 1)
        ),
        end= OutputState (
            registers=RegisterState (b=2)
        )
    ),
    /*
    The cdv instruction (opcode 7) works exactly like the adv instruction except that the
    result is stored in the C register. (The numerator is still read from the A register.)
     */
    UnitTest (
        desc="cdv 0/2",
        init= InputState (
            registers=null,
            program=listOf (7, 1)
        ),
        end= OutputState (
            registers=RegisterState (c=0)
        )
    ),
    UnitTest (
        desc="cdv 2/2",
        init= InputState (
            registers=RegisterState(a=2),
            program=listOf (7, 1)
        ),
        end= OutputState (
            registers=RegisterState (c=1)
        )
    ),
    UnitTest (
        desc="cdv 4/2",
        init= InputState (
            registers=RegisterState(a=4),
            program=listOf (7, 1)
        ),
        end= OutputState (
            registers=RegisterState (c=2)
        )
    ),
)

// EOF