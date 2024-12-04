package day3

import util.PeekableIterator
import util.interactive

sealed class Token {
    abstract val encoded: String

    data class Character (val c: Char) : Token () {
        override val encoded: String
            get () = "$c"
    }
    data class Maybe (val token: Token) : Token () {
        override val encoded: String
            get () = "?${token.encoded}"
    }
    data class Group (val tokens: List<Token>): Token () {
        override val encoded: String
          get () = "[${tokens.map { it.encoded }.joinToString("")}]"
    }
    data class Or (val options: List<List<Token>>): Token () {
        override val encoded: String
            get () = buildString {
                append ('(')
                options.forEachIndexed { i, el ->
                    if (i > 0) {
                        append("|")
                    }
                    el.forEach {
                        append (it.encoded)
                    }
                }
                append (')')
            }
    }
}

object RegexUtil {
    fun parse (input: String): List<Token> {
        val iter = PeekableIterator (input.iterator())
        val tokens = mutableListOf<Token>()
        while (iter.hasNext ()) {
            tokens.add (parseOne (iter))
        }
        return tokens
    }

    private fun parseOr (iter: PeekableIterator<Char>): Token {
        val options = mutableListOf<List<Token>> ()
        val accum = mutableListOf<Token> ()
        while (true) {
            val c = iter.peek()
            when (c) {
                ')' -> {
                    options.add (accum.toList ())
                    iter.next ()
                    break
                }
                '|' -> {
                    if (accum.isEmpty ()) {
                        throw IllegalStateException ("Empty or element.")
                    }
                    options.add (accum.toList())
                    accum.clear ()
                    iter.next ()
                }
                else -> accum.add (parseOne (iter))
            }
        }
        return Token.Or (options)
    }

    private fun parseOne (iter: PeekableIterator<Char>): Token {
        val c = iter.next ();
        return when (c) {
            '[' -> parseGroup (iter)
            '(' -> parseOr (iter)
            '?' -> Token.Maybe (parseOne (iter))
            ']' -> throw IllegalStateException ("Unexpected character: ]")
            else -> Token.Character (c)
        }
    }

    private fun parseGroup (iter: PeekableIterator<Char>): Token.Group {
        val tokens = buildList {
            while (iter.peek() != ']') {
                add (parseOne (iter))
            }
        }
        iter.next ()
        return Token.Group (tokens)
    }
}


fun main () {
    interactive { cmd ->
        if (cmd.isEmpty()) {
            true
        } else {
            absorb {
                val parsed = RegexUtil.parse (cmd)
                val buf = StringBuffer ()
                parsed.forEach {
                    println ("$it")
                    buf.append (it.encoded)
                }
                println (buf)
            }
            false
        }
    }
}

fun <T> absorb (func: () -> T) {
    try {
        func ();
    }
    catch (e: Exception) {
        println ("ERROR: ${e.message}")
        e.printStackTrace()
    }
}



// EOF