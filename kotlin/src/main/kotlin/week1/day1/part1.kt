package week1.day1

import util.InputUtil
import java.io.IOException
import java.util.regex.Pattern

val DAY = 1;
val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput (DAY, SAMPLE);
    val (left, right) = parseLists (input)
    val i1 = left.iterator ();
    val i2 = right.iterator ();
    var total = 0;
    while (i1.hasNext ()) {
        total += Math.abs (i1.next () - i2.next ());
    }
    println (total);
    return;
}

fun parseLists (input: String): Pair<List<Int>, List<Int>> {
    val left = mutableListOf<Int> ();
    val right = mutableListOf<Int> ();
    val regex = Pattern.compile ("^([0-9]+)\\s+([0-9]+)$");
    input.split ("\n").forEach {
        val matcher = regex.matcher (it);
        if (! matcher.matches()) {
            throw IOException ("No match for: \"$it\"");
        }
        left.add (matcher.group(1).toInt());
        right.add (matcher.group(2).toInt())
    }
    left.sort ();
    right.sort ();
    return left to right;
}

// EOF