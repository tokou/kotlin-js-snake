import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.dom.create
import kotlinx.html.js.*
import kotlinx.html.*

const val width = 20
const val height = 20

fun main() {

    var snake = Snake(
        cells = listOf(Cell(2, 0), Cell(1, 0), Cell(0, 0)),
        direction = Direction.Right
    )

    val loop = {
        snake = snake.move()
        draw(snake)
    }

    window.setInterval(loop, 400)
}

fun draw(snake: Snake) {
    val grid = document.create.div {
        id = "grid"
        table {
            repeat(height) { j ->
                tr {
                    repeat(width) { i ->
                        val snakeClasses = if (snake.cells.contains(Cell(i, j))) "snake" else ""
                        td("cell $snakeClasses")
                    }
                }
            }
        }
    }
    document.getElementById("grid")?.remove()
    document.body?.append(grid)
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
