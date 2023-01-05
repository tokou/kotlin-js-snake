import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.browser.window

class TestClient {
    @Test
    fun testUserAgent() {
        assertTrue { window.navigator.userAgent.contains("Mozilla") }
    }

    @Test
    fun testMove() {
        val snake = Snake(
            cells = listOf(Cell(2, 0), Cell(1, 0), Cell(0, 0)),
            direction = Direction.Right
        )
        assertEquals(
            actual = snake.move(),
            expected = Snake(
                cells = listOf(Cell(3, 0), Cell(2, 0), Cell(1, 0)),
                direction = Direction.Right
            )
        )
    }
}
