import kotlinx.browser.window

fun main() {
    console.log("Hello from  ${window.navigator.userAgent}")

    val snake = Snake(
        cells = listOf(Cell(2, 0), Cell(1, 0), Cell(0, 0)),
        direction = Direction.Right
    )
    console.log(snake)
}

data class Snake(val cells: List<Cell>, val direction: Direction) {
    fun move(): Snake {
        val newHead = cells.first().move(direction)
        val newTail = cells.dropLast(1)
        return copy(cells = listOf(newHead) + newTail)
    }
}

data class Cell(val x: Int, val y: Int) {
    fun move(direction: Direction): Cell = Cell(x + direction.dx, y + direction.dy)
}

enum class Direction(val dx: Int, val dy: Int) {
    Up(0, -1), Down(0, 1), Left(-1, 0), Right(1, 0)
}
