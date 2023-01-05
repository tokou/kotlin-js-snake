import kotlinx.browser.window

fun main() {
    console.log("Hello from  ${window.navigator.userAgent}")

    val snake = Snake(
        cells = listOf(Cell(2, 0), Cell(1, 0), Cell(0, 0)),
        direction = Direction.Right
    )
    console.log(snake)
}

data class Snake(val cells: List<Cell>, val direction: Direction)

data class Cell(val x: Int, val y: Int)

enum class Direction {
    Up, Down, Left, Right
}
