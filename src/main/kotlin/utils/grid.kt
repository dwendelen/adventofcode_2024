fun <T> loadGrid(file: String, fn: (Char) -> T): Grid<T> {
    val items = file
        .lines()
        .map { line -> line.map(fn) }
    return Grid(items)
}

data class Coordinate(
    val row: Int,
    val col: Int
) {
    operator fun minus(other: Coordinate): Vector {
        return Vector(
            row - other.row,
            col - other.col
        )
    }
    operator fun plus(vector: Vector): Coordinate {
        return Coordinate(
            row + vector.rows,
            col + vector.cols
        )
    }
}

data class Vector(
    val rows: Int,
    val cols: Int
) {
    operator fun unaryMinus(): Vector {
        return Vector(-rows, -cols)
    }
}

data class Grid<T>(
    val rows: List<List<T>>,
) {
    operator fun get(coordinate: Coordinate) = get(coordinate.row, coordinate.col)
    operator fun get(row: Int, col: Int): T? = rows.getOrNull(row)?.getOrNull(col)
    operator fun contains(coordinate: Coordinate): Boolean {
        return coordinate.row in rows.indices &&
                coordinate.col in rows[0].indices
    }

    fun itemMap(): Map<T, List<Coordinate>> {
        val acc = mutableMapOf<T, MutableList<Coordinate>>()
        forEachIndexed { coor, item ->
            acc.computeIfAbsent(item, { mutableListOf()}).add(coor)
        }
        return acc
    }
    fun forEachIndexed(action: (row: Int, col: Int, item: T) -> Unit) {
        rows.forEachIndexed { r, row ->
            row.forEachIndexed { c, cell ->
                action(r, c, cell)
            }
        }
    }
    fun forEachIndexed(action: (coordinate: Coordinate, item: T) -> Unit) {
        forEachIndexed{ r, c, i -> action(Coordinate(r, c), i)}
    }

}