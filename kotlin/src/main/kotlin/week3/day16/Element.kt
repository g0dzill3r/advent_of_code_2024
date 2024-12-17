package week3.day16

enum class Element (val symbol: Char, val render: Char) {
    EMPTY('.', '·'),
    WALL('#', '█'),
    END ('E', '◉'),
    START ('S', '◯'),
    SENTINEL ('*', '*');

    companion object {
        private val map = entries.associateBy(Element::symbol)
        fun fromSymbol (symbol: Char): Element = map[symbol] !!
    }
}


// EOF