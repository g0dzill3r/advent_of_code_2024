package week1.day3

class Matcher {
    private val root = Node ();

    /**
     * Load a pattern into the matcher
     */

    fun addPattern (pattern: String, func: (Int, String) -> Unit) {
        addPattern (RegexUtil.parse (pattern), func, listOf (root));
    }

    fun addPattern (seq: List<Token>, func: (Int, String) -> Unit, ptrs: List<Node>) {
        if (seq.isEmpty ()) {
            ptrs.forEach {
                if (it.next.isNotEmpty ()) {
                    throw Exception ("Pattern shadows previous pattern.")
                }
                it.func = func
            }
            return
        }

        val token = seq[0]
        val rest = seq.subList (1, seq.size)

        when (token) {
            is Token.Character -> {
                val update = mutableListOf<Node>()
                ptrs.forEach { node ->
                    update.addAll (node.getOrAdd (token.c))
                }
                addPattern (rest, func, update)
            }
            is Token.Group -> {
                for (el in token.tokens) {
                    val option = buildList {
                        add (el)
                        addAll (rest)
                    }
                    addPattern (option, func, ptrs)
                }
            }
            is Token.Or -> {
                for (el in token.options) {
                    val option = buildList {
                        addAll (el)
                        addAll (rest)
                    }
                    addPattern (option, func, ptrs)
                }
            }
            is Token.Maybe -> {
                // INCLUDE
                val include = buildList {
                    add (token.token)
                    addAll (rest)
                }
                addPattern (include, func, ptrs)
                // MISS
                addPattern (rest, func, ptrs)

            }
        }
    }

    /**
     * Match an input pattern against the registered expressions.
     */

    fun match (input: String) {
        var curr = mutableListOf (root to StringBuffer())

        for (i in input.indices) {
            val c = input[i]
            val updated = mutableListOf (root to StringBuffer())
            curr.forEach { (el, buf) ->
                val nodes = el.get (c)
                nodes?.forEach { node ->
                    if (node.isTerminal) {
                        node.func!! (0, buf.toString() + c)
                    } else {
                        updated.add (node to StringBuffer (buf.toString () + c) )
                    }
                }
            }
            curr = updated
        }
        return
    }

    fun dump () {
        root.dump (0)
    }

    class Node (
        var char: Char? = null,
        var func: ((Int, String) -> Unit)? = null,
        val next: MutableMap<Char, List<Node>> = mutableMapOf (),
    ) {
        val isTerminal: Boolean
            get ()= func != null

        fun hasNext (c: Char): Boolean = next.contains (c)
        fun get (c: Char): List<Node>? = next[c]
        fun getOrAdd (c: Char): List<Node> {
            if (! hasNext (c)) {
                next[c] = listOf (Node (c))
            }
            return next[c] as List<Node>
        }

        fun dump (indent: Int = 0) {
            println ("  ".repeat (indent) + "[$char]" + "  " + (if (isTerminal) "f()" else ""))
            next.keys.forEach {
                next[it]!!.forEach {
                    it.dump (indent + 1);
                }
            }
            return
        }
    }
}

fun main () {
    val matcher = Matcher ()

}

// EOF