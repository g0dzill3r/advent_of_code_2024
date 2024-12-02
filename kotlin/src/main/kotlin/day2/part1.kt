package day2

import util.InputUtil
import kotlin.math.abs

val DAY = 2;
val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE);
    val data = Reports.parse (input);
    data.reports.forEach {
        println ("$it: ${it.condition}");
    }
    val safe = data.reports.filter { it.condition == LevelCondition.SAFE }.count ();
    println (safe);
    return;
}

enum class LevelCondition {
    SAFE,
    UNSAFE
}

data class Reports (
    val reports: List<Report>
) {
    override fun toString (): String {
        return buildString {
            reports.forEach {
                println(it);
            }
        }
    }

    companion object {
        fun parse (input:String): Reports {
            return Reports (
                buildList {
                    input.split ("\n").forEach {
                        add(Report.parse(it));
                    }
                }
            );
        }
    }
}

data class Report (
    val levels: List<Int>
) {
    val isSafe: Boolean
        get () = condition == LevelCondition.SAFE;

    val isUnsafe: Boolean
        get () = condition == LevelCondition.UNSAFE;

    val deltas = buildList {
        for (level in 0 until levels.size - 1) {
            add (levels[level + 1] - levels [level]);
        }
    }

    val condition: LevelCondition
        get () {
            val deltas = deltas
            if (deltas.all { it > 0 } || deltas.all { it < 0 }) {
                if (deltas.map { abs (it) }.max() <= 3) {
                    return LevelCondition.SAFE
                }
            }
            return LevelCondition.UNSAFE;
        }

    override fun toString (): String {
        return buildString {
            levels.forEach {
                append(it);
                append(' ');
            }
        }
    }

    companion object {
        fun parse(input:String): Report {
            return Report (
                buildList {
                    input.split (Regex ("\\s+")).forEach {
                        add (it.toInt ());
                    }
                }
            );
        }
    }
}

// EOF