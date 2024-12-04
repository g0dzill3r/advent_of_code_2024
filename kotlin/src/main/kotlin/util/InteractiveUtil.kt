package util

fun interactive (prompt: String = "> ", func: (String) -> Boolean) {
    do {
        print (prompt)
        val str = readLine () as String
        try {
            if (func (str)) {
                break
            }
        } catch (t: Throwable) {
            println ("ERROR: ${t.message}")
            t.printStackTrace()
        }
    } while (true)
}

// EOF