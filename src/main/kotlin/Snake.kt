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
                        val cell = Cell(i, j)
                        val direction = snake.direction.toString().lowercase()
                        val snakeClasses = if (snake.cells.contains(cell)) "snake" else ""
                        val headClasses = if (snake.head == cell) "head $direction" else ""
                        td("cell $snakeClasses $headClasses")
                    }
                }
            }
        }
    }
    document.getElementById("grid")?.remove()
    document.body?.append(grid)
}

data class Snake(val cells: List<Cell>, val direction: Direction) {
    val head = cells.first()
    val tail = cells.drop(1)

    fun move(): Snake {
        val newHead = cells.first().move(direction)
        val newTail = cells.dropLast(1)
        return copy(cells = listOf(newHead) + newTail)
    }

    fun turn(newDirection: Direction): Snake {
        if (newDirection.isOpposite(direction)) return this
        return copy(direction = newDirection)
    }
}

data class Cell(val x: Int, val y: Int) {
    fun move(direction: Direction): Cell = Cell(x + direction.dx, y + direction.dy)
}

enum class Direction(val dx: Int, val dy: Int) {
    Up(0, -1), Down(0, 1), Left(-1, 0), Right(1, 0);

    fun isOpposite(that: Direction) = dx + that.dx == 0 && dy + that.dy == 0
}
