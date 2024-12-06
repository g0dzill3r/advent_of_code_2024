package util

fun Int.repeat (func: () -> Unit) {
    for (i in 0..this) {
        func()
    }
    return
}