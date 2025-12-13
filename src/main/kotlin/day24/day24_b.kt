import utils.asResource

fun main() {
    /*
        x00 XOR y00 -> xor
        y00 AND x00 -> and
        xor XOR cin -> z00

        xor AND cin -> hwt
        and OR hwt -> cou

        mrk = carry-in
        qkc = carry-out

        Formula
        z         = (x XOR y) XOR carry-in
        carry-out = (x AND y) OR (carry-in AND (x XOR y))
     */

//    val file = "day24/example2.txt"
    val file = "day24/input.txt"

    val lines = file.asResource().readText()
        .lines()

    val idx = lines.indexOf("")
    val lines1 = lines.subList(0, idx)
    val lines2 = lines.subList(idx + 1, lines.size)

    val nbBits = lines1.size / 2

    val gates = lines2.
            map { str ->
                val (e1, e2) = str.split(" -> ")
                val (i1, op, i2) = e1.split(" ")
                val (ii1, ii2) = listOf(i1, i2).sorted()
                Gate(e2, op, ii1, ii2)
            }

    for (b in 1 until nbBits) {
        val str = b.toString().padStart(2, '0')
        val xorGate = gates.first { it.op == "XOR" && it.i1 == "x$str" && it.i2 == "y$str" }
        val andGate = gates.first { it.op == "AND" && it.i1 == "x$str" && it.i2 == "y$str" }
        val outGate = gates.first { it.name == "z$str" }

        val andGate2 = gates.firstOrNull { it.op == "AND" && (it.i1 == xorGate.name || it.i2 == xorGate.name) }
        val orGate = gates.firstOrNull { it.op == "OR"  && (it.i1 == andGate.name || it.i2 == andGate.name) }

        val carryIn1 = if (outGate.i1 == xorGate.name) {
            outGate.i2
        } else if(outGate.i2 == xorGate.name) {
            outGate.i1
        } else {
            null
        }
        val carryIn2 = if (andGate2 != null && andGate2.i1 == xorGate.name) {
            andGate2.i2
        } else if(andGate2 != null && andGate2.i2 == xorGate.name) {
            andGate2.i1
        } else {
            null
        }

        println("---")
        println("Bit:        $b")
        println("Carry in 1: $carryIn1")
        println("Carry in 2: $carryIn2")
        println("Carry out:  " + orGate?.name)

        println("And1:       " + andGate.name)
        println("And2:       " + andGate2?.name)
        println("OR1:        " + orGate?.i1)
        println("OR2:        " + orGate?.i2)
    }
    println("---")

    /*
        Bad bit:   11
        Carry-in:  jdm
        Carry-out: sfm

        x11 XOR y11 -> gjc
        y11 AND x11 -> qjj
        qjj XOR jdm -> z11

        qjj AND jdm -> ckv
        ckv OR gjc -> sfm

        gjc <-> qjj

        ---

        Bad bit:   17
        Carry-in:  pvh
        Carry-out: wmp

        y17 XOR x17 -> rqq
        y17 AND x17 -> pqv
        rqq XOR pvh -> wmp

        pvh AND rqq -> ffg
        pqv OR ffg -> z17

        wmp <-> z17

        ---

        Bad bit:   26
        Carry-in:  qgs
        Carry-out:  vfs

        y26 XOR x26 -> kfq
        x26 AND y26 -> vfk
        kfq XOR qgs -> gvm

        kfq AND qgs -> z26
        vfk OR gvm -> vfs

        z26 <-> gvm
        ---

        Bad bit:   39
        Carry-in:  hkg
        Carry-out: chr

        y39 XOR x39 -> sbq
        x39 AND y39 -> z39
        sbq XOR hkg -> qsb

        sbq AND hkg -> vsm
        qsb OR vsm -> chr

        z39 <-> qsb
     */

    /*
        gjc,gvm,qjj,qsb,wmp,z17,z26,z39
     */
}

data class Gate(val name: String, val op: String, val i1: String, val i2: String)

