import kotlinx.browser.document
import kotlinx.html.dom.create
import kotlinx.html.js.*
import kotlinx.html.*

fun main() {
    val width = 20
    val height = 20

    val grid = document.create.div {
        table {
            repeat(height) {
                tr {
                    repeat(width) {
                        td("cell") {
                        }
                    }
                }
            }
        }
    }
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
