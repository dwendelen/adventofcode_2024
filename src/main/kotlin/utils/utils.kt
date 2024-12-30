package utils

import java.net.URL

fun String.asResource(): URL {
    return Thread.currentThread().contextClassLoader.getResource(this)!!
}

fun <T> Iterable<T>.frequencyMap(): Map<T, Int> {
    val acc = mutableMapOf<T, Int>()
    this.forEach {
        acc[it] = acc.getOrDefault(it, 0) + 1
    }
    return acc
}