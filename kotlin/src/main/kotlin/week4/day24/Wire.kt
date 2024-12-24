package week4.day24

interface Named {
    val name: String
}

interface Connection {}

/**
 * Models a wire in the circuit.
 */

class Wire (
    override val name: String,
    val input: Gate? = null,
    var value: Boolean? = null,
    val outputs: MutableList<Gate> = mutableListOf ()
): Named, Connection {
    val isInput: Boolean
        get () = input == null

    val type: String
        get () {
            return when {
                input == null -> "INPUT"
                outputs.isEmpty() -> "TERMINAL"
                else -> "WIRE"
            }
        }

    override fun toString() = "$name($value)"
    operator fun invoke (value: Boolean) {
        this.value = value
        return
    }
    fun dump () {
        println ("$name->[$outputs] = $value")
    }
}

fun main () {
    val wire = Wire ("x00", null,true)
    println (wire)
    wire (false)
    println (wire)
    wire.dump ();
    return
}
