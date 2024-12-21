package week3.day21

/**
 * Represents a directional button on the control keypad.
 */

enum class Direction (val symbol: Char, val delta: Position? = null) {
    UP('^', Position(-1, 0)),
    ACTIVATE('A', null),
    LEFT('<',  Position(0, -1)),
    DOWN('v',  Position(1, 0)),
    RIGHT('>',  Position(0, 1));

    override fun toString() = "C$symbol"

    companion object {
        private val map = entries.associateBy { it.symbol }
        fun fromSymbol(symbol: Char) = map[symbol]!!
        private val deltas = entries.filter { it.delta != null }.associateBy { it.delta }
        fun fromDelta(delta: Position) = deltas[delta]!!
    }
}

val List<Direction>.encoded: String
    get () {
        val that = this
        return buildString {
            that.forEach {
                append (it.symbol)
            }
        }
    }

// EOF