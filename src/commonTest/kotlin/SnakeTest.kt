import Direction.*
import kotlin.test.Test
import kotlin.test.assertEquals

class SnakeTest {

    private val snake = Snake(
        cells = listOf(Cell(2, 0), Cell(1, 0), Cell(0, 0)),
        direction = Right
    )

    @Test
    fun testMove() {
        assertEquals(
            actual = snake.move(),
            expected = Snake(
                cells = listOf(Cell(3, 0), Cell(2, 0), Cell(1, 0)),
                direction = Right
            )
        )
    }

    @Test
    fun testDirection() {
        assertEquals(
            actual = snake.turn(Down).move(),
            expected = Snake(
                cells = listOf(Cell(2, 1), Cell(2, 0), Cell(1, 0)),
                direction = Down
            )
        )
        assertEquals(
            actual = snake.turn(Left).move(),
            expected = Snake(
                cells = listOf(Cell(3, 0), Cell(2, 0), Cell(1, 0)),
                direction = Right
            )
        )
    }
}
