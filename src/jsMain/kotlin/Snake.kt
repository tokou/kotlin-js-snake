import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.dom.create
import kotlinx.html.js.*
import kotlinx.html.*
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent

fun main() {

    var game = Game(
        width = 20,
        height = 20,
        snake = Snake(
            cells = listOf(Cell(4, 0), Cell(3, 0), Cell(2, 0), Cell(1, 0), Cell(0, 0)),
            direction = Direction.Right
        )
    )

    var loopHandle = 0

    val onTurn: (Event) -> Unit = { event ->
        val key = (event as KeyboardEvent).key
        if (key.startsWith("Arrow")) game = game.update(Direction.valueOf(key.drop(5)))
    }

    val loop = {
        game = game.tick()
        draw(game)
        if (game.isOver) {
            window.clearInterval(loopHandle)
            document.removeEventListener("keydown", onTurn)
        }
    }

    document.addEventListener("keydown", onTurn)
    loopHandle = window.setInterval(loop, 200)
}

fun draw(game: Game) {
    val grid = document.create.div {
        id = "grid"
        table {
            repeat(game.height) { j ->
                tr {
                    repeat(game.width) { i ->
                        val cell = Cell(i, j)
                        val direction = game.snake.direction.toString().lowercase()
                        val snakeClasses = if (game.snake.cells.contains(cell)) "snake" else ""
                        val headClasses = if (game.snake.head == cell) "head $direction" else ""
                        val appleClasses = if (game.apples.cells.contains(cell)) "apple" else ""
                        td("cell $snakeClasses $headClasses $appleClasses")
                    }
                }
            }
        }
    }
    document.getElementById("grid")?.remove()
    document.body?.append(grid)
    if (game.isOver) window.alert("Game Over\nYour score is  ${game.snake.cells.size}")
}
