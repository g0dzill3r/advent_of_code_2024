package week4.day23

data class Model (val map: Map<String, List<String>>, val connections: List<Pair<String, String>>) {
    val nodes = map.keys.sorted()
    fun getConnections (node: String) = map[node]!!
    fun areConnected (n1: String, n2: String) = n2 in getConnections (n1)

    fun triples (func: (String, String, String) -> Unit) {
        map.keys.forEach { t1 ->
            map.keys.forEach { t2 ->
                map.keys.forEach { t3 ->
                    if (t1 != t2 && t2 != t3) {
                        func (t1, t2, t3)
                    }
                }
            }
        }
        return
    }

    val rings: Set<List<String>>
        get () {
            return buildSet {
                triples { a, b, c ->
                    if (getConnections (a).contains(b) && getConnections (b).contains(c) && getConnections (c).contains(a)) {
                        add (listOf (a, b, c).sorted ())
                    }
                }
            }
        }

    companion object {
        fun parse (input: String): Model {
            val map = mutableMapOf<String, MutableList<String>> ()
            val connections = mutableListOf<Pair<String, String>> ()
            input.split ("\n").forEach {
                val (c1, c2) = it.split ("-")
                val l1 = map.getOrPut (c1) { mutableListOf () }
                l1.add (c2)
                val l2 = map.getOrPut (c2) { mutableListOf () }
                l2.add (c1)
                connections.add (c1 to c2)
            }
            return Model (map, connections)
        }
    }
}