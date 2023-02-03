package fr.belkahia.snake.android

import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import kotlin.test.assertEquals
import org.junit.Test

class AndroidInstrumentedTest {

    @Test
    fun testPackageName() {
        val applicationContext = getApplicationContext<Context>()
        assertEquals("fr.belkahia.snake.android", applicationContext.packageName)
    }
}
