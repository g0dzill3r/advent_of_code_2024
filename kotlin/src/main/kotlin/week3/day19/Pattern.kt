package week3.day19

data class Pattern (val seq: String) {
    val size: Int = seq.length
    val colors: List<Color> = seq.map { Color.fromSymbol(it) }
    fun isPrefix (other: Pattern): Boolean {
        return other.colors.size <= colors.size && other.colors == colors.subList (0, other.colors.size)
    }
    fun remove (count: Int): Pattern = Pattern (seq.substring (count, seq.length))
    override fun toString (): String = seq
}

fun main() {
    val p1 = Pattern ("wubrg")
    val p2 = Pattern ("wub")
    println (p1.isPrefix(p2))
    println (p2.isPrefix(p1))

    println (p1.remove (2))
    return
}

// EOF