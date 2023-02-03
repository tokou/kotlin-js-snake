import kotlin.random.Random
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

data class Game(
    val width: Int,
    val height: Int,
    val snake: Snake,
    val apples: Apples = Apples(width, height),
    val canTurn: Boolean = true
) {

    @OptIn(ExperimentalStdlibApi::class)
    val isOver: Boolean =
        snake.tail.contains(snake.head) || snake.cells.any {
            it.x !in 0..<width || it.y !in 0..<height
        }

    fun update(direction: Direction) =
        if (!canTurn) this
        else copy(snake = snake.turn(direction), canTurn = false)

    fun tick() = copy(
        snake = snake.move(),
        apples = apples.grow(),
        canTurn = true
    )
}

data class Apples(
    val width: Int,
    val height: Int,
    val cells: Set<Cell> = emptySet(),
    val growthFactor: Int = 3,
    val random: Random = Random
) {
    fun grow(): Apples =
        if (random.nextInt(10) >= growthFactor) this
        else copy(cells = cells + Cell(random.nextInt(width), random.nextInt(height)))
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
