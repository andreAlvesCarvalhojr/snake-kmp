package com.andrealvescarvalhojr.snakekmp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun App() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SnakeGameScreen()
        }
    }
}

@Composable
private fun SnakeGameScreen() {
    val game = remember { SnakeGameState() }

    LaunchedEffect(game) {
        while (true) {
            if (!game.isGameOver) {
                delay(150)
                game.tick()
            } else {
                delay(100)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Snake KMP", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Score: ${game.score}", style = MaterialTheme.typography.titleMedium)

        Box(
            modifier = Modifier
                .padding(top = 12.dp, bottom = 20.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color(0xFF101010))
                .border(2.dp, Color(0xFF4CAF50)),
            contentAlignment = Alignment.Center,
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val cellSize = size.minDimension / game.gridSize

                game.food?.let { food ->
                    drawRect(
                        color = Color(0xFFFF5252),
                        topLeft = Offset(food.x * cellSize, food.y * cellSize),
                        size = androidx.compose.ui.geometry.Size(cellSize, cellSize),
                    )
                }

                game.snake.forEachIndexed { index, part ->
                    val color = if (index == 0) Color(0xFF66BB6A) else Color(0xFF4CAF50)
                    drawRect(
                        color = color,
                        topLeft = Offset(part.x * cellSize, part.y * cellSize),
                        size = androidx.compose.ui.geometry.Size(cellSize, cellSize),
                    )
                }
            }

            if (game.isGameOver) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.72f)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Game Over",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Final score: ${game.score}",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = game::restart) {
                        Text("Play again")
                    }
                }
            }
        }

        Controls(
            onUp = { game.changeDirection(Direction.Up) },
            onDown = { game.changeDirection(Direction.Down) },
            onLeft = { game.changeDirection(Direction.Left) },
            onRight = { game.changeDirection(Direction.Right) },
        )
    }
}

@Composable
private fun Controls(
    onUp: () -> Unit,
    onDown: () -> Unit,
    onLeft: () -> Unit,
    onRight: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = onUp, modifier = Modifier.width(84.dp)) {
            Text("↑")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onLeft, modifier = Modifier.width(84.dp)) {
                Text("←")
            }
            Button(onClick = onDown, modifier = Modifier.width(84.dp)) {
                Text("↓")
            }
            Button(onClick = onRight, modifier = Modifier.width(84.dp)) {
                Text("→")
            }
        }
    }
}
