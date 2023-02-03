import platform.posix.getpid
import ncurses.*

fun main() {
    println("Hello from ${getpid()}")

    val width = 20
    val height = 10

    initscr()
    noecho()
    curs_set(0)

    val window = newwin(height, width, 0, 0)
    box(window, 0, 0)
    mvwprintw(window, 3, 2, "Hello from ${getpid()}")

    wrefresh(window)
    wgetch(window)

    delwin(window)
    endwin()
}
