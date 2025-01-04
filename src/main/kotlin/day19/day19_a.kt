package day19

import Coordinate
import utils.asResource
import java.util.*

fun main() {
//    val file = "day19/example.txt"
    val file = "day19/input.txt"


    val fileText = file.asResource().readText()
    val (towelPart, patternPart) = fileText.split("\n\n")

    val regex = Regex("^(" + towelPart.replace(", ", "|") + ")*$")
    val result = patternPart.lines()
        .count {
            it.matches(regex)
        }

    println(result)
}
