package week1.day5

enum class Ordering {
    BEFORE,
    AFTER,
    NEITHER
}

data class Rules (
    val ordering: List<Pair<Int, Int>>,
    val updates: List<List<Int>>
) {
    /**
     * Determines if the update is properly ordered according to the ordering rules.
     */

    fun isOrdered (update: List<Int>): Boolean {
        for (i in update.indices) {
            val before = update[i]
            for (j in i + 1 until update.size) {
                val after = update[j]
                val ordering = ordering (before, after)
                if (ordering == Ordering.AFTER) {
                    return false
                }
            }
        }
        return true
    }

    fun comesBefore (a: Int, b: Int): Boolean {
        ordering.forEach { (before, after) ->
            if (before == a && after == b) {
                return true
            }
        }
        return false
    }

    fun comesAfter (a: Int, b: Int): Boolean = comesBefore (b, a)

    fun reorder (els: List<Int>): List<Int> {
        return els.sortedWith { a, b ->
            if (comesBefore (a, b)) {
                -1
            } else if (comesAfter (a, b)) {
                1
            } else {
                0
            }
        }
    }

    fun ordering (a: Int, b: Int): Ordering {
        return if (comesBefore (a, b)) {
            Ordering.BEFORE
        } else if (comesAfter (a, b)) {
            Ordering.AFTER
        } else {
            Ordering.NEITHER
        }
    }

    companion object {
        fun parse (input: String): Rules {
            val rows = input.split("\n")
            val iter = rows.iterator()
            val ordering = mutableListOf<Pair<Int, Int>> ()
            val updates = mutableListOf<List<Int>> ()

            while (true) {
                val row = iter.next ();
                if (row == "") {
                    break
                }
                val i = row.indexOf ("|")
                val a = row.substring (0, i).toInt()
                val b = row.substring (i + 1, row.length).toInt()
                ordering.add(Pair(a, b))
            }
            while (iter.hasNext ()) {
                val row = iter.next ()
                val els = row.split (",").map { it.toInt () }
                updates.add (els)
            }
            return Rules (ordering, updates)
        }
    }
}

// EOF