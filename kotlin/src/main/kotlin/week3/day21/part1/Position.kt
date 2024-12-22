package week3.day21.part1

import week3.day21.sign

/**
 *
 */

data class Position (val row: Int, val col: Int) {
    override fun toString () = "($row, $col)"

    operator fun minus (other: Position): Position = Position (row - other.row, col - other.col)

    companion object {
        fun toDeltas (path: List<Position>): List<Position> {
            return buildList {
                for (i in 1 until path.size) {
                    add(path[i] - path[i - 1])
                }
            }
        }

        fun toDirections (path: List<Position>): List<Direction> {
            return toDeltas (path).map { Direction.fromDelta (it) }
        }

        fun traversals (from: Position, to: Position, missing: Position): List<List<Position>> {
            val raw = naiveTraversals (from, to)
            return raw.filter { missing !in it }
        }

        private fun naiveTraversals (from: Position, to: Position, path: List<Position> = listOf (), complete: MutableList<List<Position>> = mutableListOf ()): List<List<Position>> {
            if (from == to) {
                complete.add (buildList {
                    addAll (path)
                    add (to)
                })
            }
            val deltaRow = buildList {
                add (0)
                val maybe = sign (to.row - from.row)
                if (maybe != 0) {
                    add (maybe)
                }
            }
            val deltaCol = buildList {
                add (0)
                val maybe = sign (to.col - from.col)
                if (maybe != 0) {
                    add (maybe)
                }
            }
            for (row in deltaRow) {
                for (col in deltaCol) {
                    if (row * col == 0 && (row != 0 || col != 0)) {
                        val update = Position(from.row + row, from.col + col)
                        naiveTraversals(
                            update,
                            to,
                            buildList {
                                addAll(path)
                                add(from)
                            },
                            complete
                        )
                    }
                }
            }
            return complete
        }
    }
}


// EOF