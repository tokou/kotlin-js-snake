import kotlin.test.Test
import kotlin.test.assertTrue
import kotlinx.browser.window

class BrowserTest {
    @Test
    fun testUserAgent() {
        assertTrue { window.navigator.userAgent.contains("Mozilla") }
    }
}
