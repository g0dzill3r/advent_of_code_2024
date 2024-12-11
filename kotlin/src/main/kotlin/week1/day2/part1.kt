package week1.day2

import util.InputUtil
import kotlin.math.abs

val DAY = 2;
val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    val data = Reports.parse (input)

    val safe = data.reports.filter { it.isSafe }.count ()
    println (safe)
    return
}

enum class LevelCondition {
    SAFE,
    UNSAFE
}

data class Reports (
    val reports: List<Levels>
) {
    override fun toString (): String {
        return buildString {
            reports.forEach {
                println(it)
            }
        }
    }

    companion object {
        fun parse (input:String): Reports {
            return Reports (
                buildList {
                    input.split ("\n").forEach {
                        add (Levels.parse(it))
                    }
                }
            );
        }
    }
}

data class Levels (
    val levels: List<Int>
) {
    val isSafe: Boolean
        get () = condition == LevelCondition.SAFE

    val isUnsafe: Boolean
        get () = condition == LevelCondition.UNSAFE

    override fun toString (): String {
        return buildString {
            levels.forEach {
                append (it)
                append (' ')
            }
        }
    }

    val deltas = buildList {
        for (level in 0 until levels.size - 1) {
            add (levels[level + 1] - levels [level])
        }
    }

    val isFixable: Boolean
        get () {
            val flaws = flaws
            if (flaws.isEmpty ()) {
                throw IllegalStateException("Levels are flawless.")
            }
            for (i in levels.indices) {
                if (remove (i).isSafe) {
                    return true
                }
            }
            return false
        }

    val condition: LevelCondition
        get () {
            val deltas = deltas
            if (deltas.allPositive || deltas.allNegative) {
                if (deltas.map { abs (it) }.all  { it in 1..3 }) {
                    return LevelCondition.SAFE
                }
            }
            return LevelCondition.UNSAFE;
        }

    fun remove (index: Int): Levels {
            val copy = levels.toMutableList()
            copy.removeAt (index)
            return Levels (copy)
        }

    val flaws: List<Int>
        get () {
            val sign = sign
            return buildList {
                deltas.forEachIndexed { index, delta ->
                    if (sign == 1) {
                        if (delta !in 1 .. 3) {
                            add (index + 1);
                        }
                    } else {
                        if (delta !in -3 .. -1) {
                            add (index + 1)
                        }
                    }
                }
            }
        }

    val sign: Int
        get () {
            var neg = 0
            var pos = 0
            deltas.forEach {
                if (it < 0) {
                    neg ++;
                } else if (it > 0) {
                    pos ++
                }
            }
            return if (pos > neg) {
                1
            } else {
                -1
            }
        }

    companion object {
        fun parse(input:String): Levels {
            return Levels (
                buildList {
                    input.split (Regex ("\\s+")).forEach {
                        add (it.toInt ());
                    }
                }
            )
        }
    }
}

val List<Int>.allPositive: Boolean
    get () = all { it > 0 }

val List<Int>.allNegative: Boolean
    get () = all { it < 0 }



// EOF