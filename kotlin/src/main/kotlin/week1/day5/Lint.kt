package week1.day5

import util.withInput

fun main () {
    withInput (DAY, false) { input ->
        val rules = Rules.parse(input)

        // How many pages are referenced in the ordering rules

        val referenced = buildSet {
            rules.ordering.forEach { (a, b) ->
                add (a)
                add (b)
            }
        }
        println ("${referenced.size} pages referenced in the ordering rules")

        // How many pages are there

        val pages = buildSet {
            rules.updates.forEach {
                it.forEach {
                    add (it)
                }
            }
        }
        println ("${pages.size} pages found.")

        // Can I reorder perfectly using the rules

        val allPages = pages.toList ()
        val perfect = rules.reorder (allPages)
        println (perfect)

        // Let's see what the graph looks like

        data class Node (
            val value: Int,
            val inputs: MutableList<Node> = mutableListOf (),
            val outputs: MutableList<Node> = mutableListOf ()
        )
        val nodes = mutableMapOf<Int, Node> ()
        allPages.forEach {
            nodes[it] = Node (it)
        }
        rules.ordering.forEach { (a, b) ->
            val an = nodes[a] as Node
            val bn = nodes[b] as Node
            an.outputs.add (bn)
            bn.inputs.add (an)
        }

        // For the perfect ordering show the graph

//        perfect.forEach {
//            println ("$it: ${nodes[it]?.outputs?.map { it.value }}")
//        }

        // Now for each node, lets show that the graph traversal terminates

        val cycles = mutableListOf<Int> ()
        allPages.forEach { page ->
            val start = nodes[page] as Node
            var curr = start.outputs
            val seen = mutableSetOf<Int> ()
            outer@while (curr.isNotEmpty ()) {
                val repl = mutableListOf<Node> ()
                for (el in curr) {
                    repl.addAll (el.outputs)
                    if (seen.contains (el.value)) {
//                        println ("Cycle detected for page $page")
                        cycles.add (page)
                        break@outer
                    }
                    seen.add (el.value)
                }
                curr = repl
            }
        }
        println ("There are ${cycles.size} cycles in the page ordering graph")
    }
    return
}

// EOF