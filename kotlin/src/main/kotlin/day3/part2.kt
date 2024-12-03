package day3

import util.InputUtil
import java.util.regex.Pattern

private val pattern = Pattern.compile ("mul\\([0-9]{1,3},[0-9]{1,3}\\)")
private val subpattern = Pattern.compile ("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)")

sealed class Entry {
    data class Mul (val value: Int) : Entry ()
    class Do: Entry ()
    class Dont: Entry ()
}

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    println (input)
    val muls = mutableListOf<Pair<Int, Entry>> ()

    // Find the mul instructions

    fun process (pattern: String, input: String, func: (Int, String) -> Unit) {
        val matcher = Pattern.compile (pattern).matcher (input)
        while (matcher.find ()) {
            val start = matcher.start()
            val end = matcher.end()
            func (start, input.substring(start, end))
        }
        return
    }

    process ("mul\\([0-9]{1,3},[0-9]{1,3}\\)", input) { i, str ->
        val m2 = subpattern.matcher (str)
        if (m2.matches ()) {
            val l = m2.group (1).toInt ()
            val r = m2.group (2).toInt ()
            muls.add (i to Entry.Mul(l * r))
        }
    }

    // Find the do and don't instructions

    process ("do\\(\\)", input) { i, str ->
        muls.add (i to Entry.Do())
    }

    process ("don't\\(\\)", input) { i, str ->
        muls.add (i to Entry.Dont ())
    }

    // Sort them in order of appearance

    muls.sortWith { a, b ->
        a.first - b.first
    }

    // Then evaluate the value

    var total = 0
    var enabled = true

    muls.map { it.second }.forEach {
        when (it) {
            is Entry.Do -> enabled = true
            is Entry.Dont -> enabled = false
            is Entry.Mul -> if (enabled) {
                total += it.value
            }
        }
    }
    println (total);
    return
}

// EOF
