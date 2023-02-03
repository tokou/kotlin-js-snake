package fr.belkahia.snake.android

import Cell
import Direction.*
import Game
import Snake
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val game = Game(
                width = 10,
                height = 10,
                snake = Snake(
                    cells = listOf(Cell(4, 0), Cell(3, 0), Cell(2, 0), Cell(1, 0), Cell(0, 0)),
                    direction = Right
                )
            )

            Column {
                var state by remember { mutableStateOf(game) }

                LaunchedEffect(state, !state.isOver) {
                    if (state.isOver) return@LaunchedEffect
                    delay(200)
                    state = state.tick()
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(state.width),
                    modifier = Modifier.aspectRatio(1f).fillMaxWidth(),
                    userScrollEnabled = false
                ) {
                    items(state.height * state.width) {
                        val i = it % state.width
                        val j = it / state.width
                        val cell = Cell(i, j)
                        var modifier = Modifier.aspectRatio(1f).border(1.dp, Color.Black)
                        if (state.apples.cells.contains(cell)) modifier = modifier.background(Color.Red)
                        if (state.snake.cells.contains(cell)) modifier = modifier.background(Color.Green)
                        if (state.snake.head == cell) modifier = modifier.background(Color.Blue)
                        Box(modifier = modifier)
                    }
                }
                if (state.isOver) {
                    Spacer(Modifier.height(60.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red)
                    ) {
                        Text(
                            text = "Game Over",
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.displayLarge,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Your score is ${state.score}",
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.displayMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(Modifier.height(60.dp))
                Column(
                    modifier = Modifier.fillMaxWidth().onKeyEvent {
                        when (it.key) {
                            Key.DirectionUp -> {
                                state = state.update(Up)
                                true
                            }
                            Key.DirectionLeft -> {
                                state = state.update(Left)
                                true
                            }
                            Key.DirectionRight -> {
                                state = state.update(Right)
                                true
                            }
                            Key.DirectionDown -> {
                                state = state.update(Down)
                                true
                            }
                            else -> false
                        }
                    },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (state.isOver) {
                        Row {
                            Button({ state = game }) {
                                Text("Restart")
                            }
                        }
                    } else {
                        Row {
                            Button({ state = state.update(Up) }) {
                                Text("Up")
                            }
                        }
                        Row {
                            Button({ state = state.update(Left) }) {
                                Text("Left")
                            }
                            Spacer(Modifier.width(40.dp))
                            Button({ state = state.update(Right) }) {
                                Text("Right")
                            }
                        }
                        Row {
                            Button({ state = state.update(Down) }) {
                                Text("Down")
                            }
                        }
                    }
                }
            }
        }
    }
}
