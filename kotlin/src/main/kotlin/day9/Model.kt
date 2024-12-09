package day9

import java.math.BigInteger

sealed class Entry (val len: Int) {
    class File (len: Int, val id: Int): Entry (len) {
        override fun toString (): String = "File(len=$len, id=$id)"
    }
    class Free (len: Int): Entry (len) {
        override fun toString (): String = "Free(len=$len)"
    }
}

data class Block (val id: Int = -1)

data class Model (val input: String) {
    val entries = toEntries (input)
    var blocks = toBlocks (entries)

    val encoded: String
        get () {
            return buildString {
                blocks.forEach { block ->
                    if (block.id == -1) {
                        append (".")
                    } else {
                        append (block.id)
                    }
                }
            }
        }

    fun checksum (): BigInteger {
        var total = 0.toBigInteger()
        blocks.forEachIndexed { i, block ->
            if (block.id != -1) {
                val local = (block.id * i).toBigInteger()
                total = total.add (local)
            }
        }
        return total
    }

    /**
     * Find a file with the specified id as an index into the entries list.
     */

    private fun findFile (id: Int): Int {
        for (i in entries.indices) {
            val entry = entries[i]
            if (entry is Entry.File && entry.id == id) {
                return i
            }
        }
        throw IllegalStateException ("File not found (id=$id)")
    }

    /**
     * Find the first free block of the specified size.
     */

    private fun findFree (len: Int): Int {
        for (i in entries.indices) {
            val entry = entries[i]
            if (entry is Entry.Free && entry.len >= len) {
                return i
            }
        }
        return -1
    }

    /**
     * Compaction algorithm for part2.
     */

    fun compact2 () {
        val moves = entries.filter { it is Entry.File }
            .map { it as Entry.File }
            .sortedByDescending { it.id }
            .map { it.id }

        for (id in moves) {
            val index = findFile (id)
            val move = entries [index]
            val free = findFree (move.len)
            if (free != -1 && free < index) {
                val e2 = entries.removeAt (index)
                entries.add (index, Entry.Free (e2.len))
                val e1 = entries.removeAt (free)
                entries.add (free, e2)
                if (move.len < e1.len) {
                    entries.add (free + 1, Entry.Free(e1.len - move.len))
                }
                blocks = toBlocks (entries)
            }
        }
        return
    }

    /**
     * Compaction algorithm for part1.
     */

    fun compact () {
        var start = 0
        var end = blocks.size - 1

        while (true) {

            // Find the next free block on the disk

            while (true) {
                if (blocks[start].id != -1) {
                    start++
                } else {
                    break
                }
            }

            // Now find the last use block on the disk

            while (true) {
                if (blocks[end].id == -1) {
                    end--
                } else {
                    break
                }
            }

            if (start >= end) {
                break
            }

            swap (start, end)
        }

        return
    }

    private fun swap (a: Int, b: Int) {
        val t1 = blocks.removeAt (b)
        val t2 = blocks.removeAt (a)
        blocks.add (a, t1)
        blocks.add (b, t2)
        return
    }

    companion object {
        fun toEntries (input: String): MutableList<Entry> {
            return buildList {
                input.forEachIndexed { i, c ->
                    if (i % 2 == 0) {
                        add (Entry.File("$c".toInt(), i / 2))
                    } else {
                        add (Entry.Free ("$c".toInt ()))
                    }
                }
            }.toMutableList()
        }

        fun toBlocks (entries: List<Entry>): MutableList<Block> {
            return buildList {
                entries.forEach { entry ->
                    when (entry) {
                        is Entry.File -> {
                            for (i in 0 until entry.len) {
                                add (Block (entry.id))
                            }
                        }
                        is Entry.Free -> {
                            for (i in 0 until entry.len) {
                                add (Block ())
                            }
                        }
                    }
                }
            }.toMutableList()
        }
    }
}

// EOF