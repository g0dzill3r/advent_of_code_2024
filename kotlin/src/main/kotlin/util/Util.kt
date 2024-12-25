package util

fun Int.repeat (func: () -> Unit) {
    for (i in 0 until this) {
        func()
    }
    return
}

fun Int.forEach (func: (Int) -> Unit) {
    for (i in 0 until this) {
        func (i)
    }
    return
}

fun halt (): Nothing {
    throw IllegalStateException()
}