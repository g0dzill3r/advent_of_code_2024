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

    // Find the mul instructions

    val muls = mutableListOf<Pair<Int, Entry>> ()
    val matcher = pattern.matcher(input)

    while (matcher.find()) {
        val start = matcher.start()
        val end = matcher.end()
        val m2 = subpattern.matcher (input.substring(start, end))
        if (m2.matches ()) {
            val l = m2.group (1).toInt ()
            val r = m2.group (2).toInt ()
            muls.add (start to Entry.Mul(l * r))
        }
    }

    // Find the do and don't instructions

    val doPattern = Pattern.compile ("do\\(\\)").matcher (input)
    while (doPattern.find ()) {
        val start = doPattern.start ()
        muls.add (start to Entry.Do())
    }

    val dontPattern = Pattern.compile ("don't\\(\\)").matcher (input)
    while (dontPattern.find ()) {
        val start = dontPattern.start ()
        muls.add (start to Entry.Dont())
    }

    // Sort them

    muls.sortWith { a, b ->
        a.first - b.first
    }
    muls.forEach {
        println (it)
    }

    // Then evaluate the value

    var total = 0
    var enabled = true

    muls.forEach { mul ->
        when (mul.second) {
            is Entry.Do -> enabled = true
            is Entry.Dont -> enabled = false
            is Entry.Mul -> if (enabled) {
                total += (mul.second as Entry.Mul).value
            }
        }
    }
    println (total);
    return
}

// EOF
