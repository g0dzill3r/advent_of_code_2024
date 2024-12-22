package week4.day22

import util.repeat

/*
Calculate the result of multiplying the secret number by 64.
Then, mix this result into the secret number. Finally, prune the secret number.

Calculate the result of dividing the secret number by 32.
Round the result down to the nearest integer.
Then, mix this result into the secret number.
Finally, prune the secret number.

Calculate the result of multiplying the secret number by 2048.
 Then, mix this result into the secret number. Finally, prune the secret number.

Each step of the above process involves mixing and pruning:

To mix a value into the secret number, calculate the bitwise XOR of the given value and the secret number. Then, the secret number becomes the result of that operation. (If the secret number is 42 and you were to mix 15 into the secret number, the secret number would become 37.)
To prune the secret number, calculate the value of the secret number modulo 16777216. Then, the secret number becomes the result of that operation. (If the secret number is 100000000 and you were to prune the secret number, the secret number would become 16113920.)
 */

data class Secret (var value: Long) {
    fun after (count: Int): Secret {
        var secret = this
        count.repeat {
            secret = secret.next
        }
        return secret
    }

    val price: Int = (value % 10).toInt ()

    val next: Secret
        get () {
            // mix
            var tmp = value xor (value * 64L)
            // prune
            tmp = tmp % 16777216L
            // mix
            tmp = tmp xor (tmp / 32L)
            // prune
            tmp = tmp % 16777216L
            // mix
            tmp = tmp xor (tmp * 2048L)
            // prune
            tmp = tmp % 16777216L
            return Secret (tmp)
        }
}

fun main () {
    var secret = Secret (123)
    11.repeat {
       println (secret)
       secret = secret.next
    }
}

// EOF