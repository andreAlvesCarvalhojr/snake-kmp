package com.andrealvescarvalhojr.snakekmp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random

data class Cell(val x: Int, val y: Int)

enum class Direction {
    Up,
    Down,
    Left,
    Right,
}

private fun Direction.isOpposite(other: Direction): Boolean {
    return (this == Direction.Up && other == Direction.Down) ||
        (this == Direction.Down && other == Direction.Up) ||
        (this == Direction.Left && other == Direction.Right) ||
        (this == Direction.Right && other == Direction.Left)
}

class SnakeGameState(
    val gridSize: Int = 20,
    private val random: Random = Random.Default,
) {
    var snake by mutableStateOf(initialSnake())
        private set

    var direction by mutableStateOf(Direction.Right)
        private set

    var food by mutableStateOf(generateFood(initialSnake()))
        private set

    var score by mutableStateOf(0)
        private set

    var isGameOver by mutableStateOf(false)
        private set

    private var nextDirection: Direction? = null

    fun changeDirection(newDirection: Direction) {
        if (newDirection != direction && !newDirection.isOpposite(direction)) {
            nextDirection = newDirection
        }
    }

    fun tick() {
        if (isGameOver) return

        nextDirection?.let {
            if (!it.isOpposite(direction)) {
                direction = it
            }
            nextDirection = null
        }

        val currentHead = snake.first()
        val newHead = when (direction) {
            Direction.Up -> Cell(currentHead.x, currentHead.y - 1)
            Direction.Down -> Cell(currentHead.x, currentHead.y + 1)
            Direction.Left -> Cell(currentHead.x - 1, currentHead.y)
            Direction.Right -> Cell(currentHead.x + 1, currentHead.y)
        }

        val willGrow = newHead == food
        val collisionBody = if (willGrow) snake else snake.dropLast(1)

        if (newHead.x !in 0 until gridSize || newHead.y !in 0 until gridSize || newHead in collisionBody) {
            isGameOver = true
            return
        }

        val updatedSnake = buildList {
            add(newHead)
            addAll(if (willGrow) snake else snake.dropLast(1))
        }
        snake = updatedSnake

        if (willGrow) {
            score += 10
            val nextFood = generateFood(updatedSnake)
            if (nextFood == null) {
                isGameOver = true
            } else {
                food = nextFood
            }
        }
    }

    fun restart() {
        val initialSnake = initialSnake()
        snake = initialSnake
        direction = Direction.Right
        nextDirection = null
        score = 0
        isGameOver = false
        val nextFood = generateFood(initialSnake)
        if (nextFood == null) {
            isGameOver = true
        } else {
            food = nextFood
        }
    }

    private fun initialSnake(): List<Cell> {
        val center = gridSize / 2
        return listOf(
            Cell(center, center),
            Cell(center - 1, center),
            Cell(center - 2, center),
        )
    }

    private fun generateFood(occupied: List<Cell>): Cell? {
        val availableCells = buildList {
            for (x in 0 until gridSize) {
                for (y in 0 until gridSize) {
                    val cell = Cell(x, y)
                    if (cell !in occupied) add(cell)
                }
            }
        }

        if (availableCells.isEmpty()) return null
        return availableCells[random.nextInt(availableCells.size)]
    }
}
