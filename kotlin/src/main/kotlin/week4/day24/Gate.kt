package week4.day24

data class Configuration (
    val inputs: List<String> = emptyList (),
    val outputs: List<String> = emptyList ()
)

sealed class Gate (
    val config: Configuration,
    val func: (Boolean, Boolean) -> Boolean,
    val inputs: MutableList<Wire> = mutableListOf (),
    val outputs: MutableList<Wire> = mutableListOf ()
) {
    class AND (config: Configuration):  Gate (config, { a, b -> a and b })
    class OR (config: Configuration): Gate (config, { a, b -> a or b })
    class XOR (config: Configuration): Gate (config, { a, b -> a xor b })

    operator fun invoke (inputs: List<Boolean>): Boolean {
        var value = inputs[0]
        for (i in 1 until inputs.size) {
            value = func (value, inputs[1])
        }
        return value
    }

    override fun toString () = "${this::class.java.simpleName}(${inputs.map { it.name }}) -> ${outputs.map { it.name }}"

    companion object {
        fun new (type: String, config: Configuration): Gate {
            return when (type) {
                "AND" -> AND (config)
                "OR" -> OR (config)
                "XOR" -> XOR (config)
                else -> throw IllegalStateException ("Unknown gate type: $type")
            }
        }
    }
}

fun main () {
    val config = Configuration ()
    val and = Gate.AND(config)
    println (and)
    println (and (listOf (true, false)))

    val or = Gate.new ("OR", config)
    println (or)
    return
}

// EOF