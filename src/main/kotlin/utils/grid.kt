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
    fun neighbours(): List<Coordinate> {
        return listOf(
            Coordinate(row - 1, col),
            Coordinate(row + 1, col),
            Coordinate(row, col - 1),
            Coordinate(row, col + 1),
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

    fun rotateCw(): Vector {
        return Vector(cols, -rows)
    }

    fun rotateCcw(): Vector {
        return Vector(-cols, rows)
    }
}

data class Grid<T>(
    val rows: List<List<T>>,
) {
    operator fun get(coordinate: Coordinate) = get(coordinate.row, coordinate.col)
    operator fun get(row: Int, col: Int): T? = rows.getOrNull(row)?.getOrNull(col)
    operator fun set(coordinate: Coordinate, value: T): Grid<T> {
        return Grid(rows.mapIndexed { r, row ->
            row.mapIndexed { c, cell ->
                if(r == coordinate.row && c == coordinate.col) {
                    value
                } else {
                    cell
                }
            }
        })
    }
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
    fun coordinates(): List<Coordinate> {
        val acc = mutableListOf<Coordinate>()
        forEachIndexed { coor, _ ->
            acc.add(coor)
        }
        return acc
    }
}