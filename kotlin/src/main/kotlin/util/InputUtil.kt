package util

import java.net.URL

object InputUtil {
    private val classLoader: ClassLoader
        get() = Thread.currentThread().contextClassLoader

    fun getInput (day: Int, sample: Boolean): String {
        val path = "day$day/input${if (sample) ".sample" else ""}";
        val url = getPath (path);
        return url.readText(Charsets.UTF_8);
    }

    fun getPath (path: String): URL = classLoader.getResource (path)
}

// EOF