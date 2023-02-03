import kotlin.random.Random

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

    fun tick(): Game {
        val (newSnake, newApples) = snake.move().eat(apples.grow())
        return copy(snake = newSnake, apples = newApples, canTurn = true)
    }
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

data class Snake(
    val cells: List<Cell>,
    val direction: Direction,
    val eatenApples: Int = 0
) {
    val head = cells.first()
    val tail = cells.drop(1)

    fun move(): Snake {
        val newHead = cells.first().move(direction)
        val newTail = if (eatenApples == 0) cells.dropLast(1) else cells
        return copy(cells = listOf(newHead) + newTail, eatenApples = maxOf(0, eatenApples - 1))
    }

    fun turn(newDirection: Direction): Snake =
        if (newDirection.isOpposite(direction)) this
        else copy(direction = newDirection)

    fun eat(apples: Apples): Pair<Snake, Apples> =
        if (!apples.cells.contains(head)) this to apples
        else copy(eatenApples = eatenApples + 1) to apples.copy(cells = apples.cells - head)
}

data class Cell(val x: Int, val y: Int) {
    fun move(direction: Direction): Cell = Cell(x + direction.dx, y + direction.dy)
}

enum class Direction(val dx: Int, val dy: Int) {
    Up(0, -1), Down(0, 1), Left(-1, 0), Right(1, 0);

    fun isOpposite(that: Direction) = dx + that.dx == 0 && dy + that.dy == 0
}
