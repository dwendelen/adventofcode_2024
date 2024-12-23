package utils

import java.net.URL

fun String.asResource(): URL {
    return Thread.currentThread().contextClassLoader.getResource(this)!!
}