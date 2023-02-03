import kotlin.test.Test
import kotlin.test.assertContains
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toKString
import platform.posix.uname
import platform.posix.utsname

class NativeTest {

    @Test
    fun testNative() = memScoped {
        val name = alloc<utsname>()
        uname(name.ptr)
        assertContains(arrayOf("Linux", "Darwin"), name.sysname.toKString())
    }
}
