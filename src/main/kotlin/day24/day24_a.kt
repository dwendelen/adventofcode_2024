import utils.asResource

fun main() {
//    val file = "day24/example1.txt"
    val file = "day24/input.txt"

    val lines = file.asResource().readText()
        .lines()

    val idx = lines.indexOf("")
    val lines1 = lines.subList(0, idx)
    val lines2 = lines.subList(idx + 1, lines.size)

    val instr1 = lines1.associate { str ->
        val (p1, p2) = str.split(": ")
        p1 to Const(p2 == "1")
    }

    val expressions = lines2.associate { str ->
        val (p1, p2) = str.split(" -> ")
        p2 to p1
    }

    val instr2 = mutableMapOf<String, Expr>()
    fun resolve(name: String): Expr {
        return if(name in instr2) {
            instr2[name]!!
        } else if(name in instr1) {
            instr1[name]!!
        } else {
            val (p1, p2, p3) = expressions[name]!!.split(" ")
            val e1 = resolve(p1)
            val e2 = resolve(p3)
            val exp = when(p2) {
                "XOR" -> Xor(e1, e2)
                "OR" -> Or(e1, e2)
                "AND" -> And(e1, e2)
                else -> throw RuntimeException("Unknown operator: $name")
            }
            instr2[name] = exp
            exp
        }
    }
    expressions.forEach { resolve(it.key) }

    val instr = instr1 + instr2
    val result = instr
        .entries
        .filter { it.key.startsWith("z") }
        .sortedBy { it.key }
        .reversed()
        .fold(0L) { acc, (_, v) ->
            2 * acc + (if (v.calc()) {
                1
            } else {
                0
            })
        }
    println(result)
}

interface Expr {
    fun calc(): Boolean
}
data class Const(val value: Boolean): Expr {
    override fun calc(): Boolean {
        return value
    }
}
data class Xor(val i1: Expr, val i2: Expr): Expr {
    override fun calc(): Boolean {
        return i1.calc() xor i2.calc()
    }
}

data class Or(val i1: Expr, val i2: Expr): Expr {
    override fun calc(): Boolean {
        return i1.calc() or i2.calc()
    }
}

data class And(val i1: Expr, val i2: Expr): Expr {
    override fun calc(): Boolean {
        return i1.calc() and i2.calc()
    }
}