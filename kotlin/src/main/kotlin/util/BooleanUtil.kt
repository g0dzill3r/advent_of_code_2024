package util

object BooleanUtil {
    val map = mapOf (
        true to '✅',
        false to '❌'
    );
}

val Boolean.symbol: Char
    get () = BooleanUtil.map [this] as Char

// EOF