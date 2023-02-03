package fr.belkahia.snake.android

import kotlin.test.assertEquals
import org.junit.Test

class AndroidUnitTest {

    @Test
    fun testJava() {
        assertEquals("Java Virtual Machine Specification", System.getProperty("java.vm.specification.name"))
    }
}
