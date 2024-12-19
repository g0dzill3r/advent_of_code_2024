package week3.day19

enum class Color (val symbol: Char) {
    WHITE ('w'),
    BLUE ('u'),
    BLACK ('b'),
    RED ('r'),
    GREEN ('g');

    companion object {
        private val map = entries.associateBy(Color::symbol)
        fun fromSymbol(symbol: Char) = map[symbol] as Color
    }
}

data class Model (val available: List<Pattern>, val desired: List<Pattern>) {
    data class Node (
        val char: Char,
//        val pattern: Pattern? = null,
        val next: MutableMap<Char, Node> = mutableMapOf (),
    ) {
        fun has (other: Color): Boolean = get (other) != null
        fun get (other: Color): Node? = next[other.symbol]

        fun put (node: Node, other: Pattern) {
            var cur = node
            other.colors.forEach {
                cur = cur.next.getOrPut (it.symbol) { Node (it.symbol) }
            }
        }
    }

    fun possible (pattern: Pattern): Boolean {
        var cur: Node = nodes
        pattern.colors.forEach {
            if (! cur.has (it)) {
                return false
            }
            cur = cur.get (it) !!
        }
        return true
    }

    val nodes: Node by lazy {
        val root = Node ('*')
        available.forEach {
            root.put (root, it)
        }
        root
    }

//    fun possible (pattern: Pattern): Boolean = allPossible (pattern).isNotEmpty()
//
//    fun allPossible (pattern: Pattern): List<List<Pattern>> {
//        return buildList {
//            available.forEach { p0 ->
//                if (pattern.isPrefix (p0)) {
//                    if (pattern.colors.size == p0.colors.size) {
//                        add (listOf (p0))
//                    } else {
//                        allPossible (pattern.remove (p0.size)).forEach {
//                            add (buildList {
//                                add (p0)
//                                addAll (it)
//                            })
//                        }
//                    }
//                }
//            }
//        }
//    }

    companion object {
        fun parse (input: String): Model {
            val iter = input.split ("\n").iterator()
            val available = iter.next ()
                .split (", ")
                .map { Pattern (it) }
            iter.next ()
            val desired = buildList {
                while (iter.hasNext ()) {
                    add (Pattern(iter.next()))
                }
            }
            return Model (available, desired)
        }
    }
}

fun main () {
    val model = Model (
        available = listOf (
            Pattern("b"),
            Pattern ("u"),
            Pattern ("bu")
        ),
        desired = listOf (
            Pattern ("bubub")
        )
    )
//    println (model.allPossible(Pattern ("bubub")))
    return
}

// EOF