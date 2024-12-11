package week1.day3

import util.InputUtil
import java.util.regex.Pattern
import kotlin.math.abs

val DAY = 3;
val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    println (input);
    val pattern = Pattern.compile ("mul\\([0-9]{1,3},[0-9]{1,3}\\)")
    val subpattern = Pattern.compile ("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)")
    val matcher = pattern.matcher(input)
    var total = 0
    while (matcher.find()) {
        val start = matcher.start()
        val end = matcher.end()
        val m2 = subpattern.matcher (input.substring(start, end))
        if (m2.matches ()) {
            val l = m2.group (1).toInt ()
            val r = m2.group (2).toInt ()
            total += l * r
        }
    }
    println (total);
    return
}

// EOF