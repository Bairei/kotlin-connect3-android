package com.bairei.connect3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var activePlayer: Int = 0 // false - yellow, true - red
    var gameState : IntArray = intArrayOf(2,2,2,2,2,2,2,2,2) // 2 - unplayed
    var winningPositions : Array<IntArray> = arrayOf(intArrayOf(0,1,2), intArrayOf(3,4,5), intArrayOf(6,7,8),
            intArrayOf(0,3,6), intArrayOf(1,4,7), intArrayOf(2,5,8), intArrayOf(0,4,8), intArrayOf(2,4,6))
    var isActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun dropIn(view: View) {
            val counter: ImageView = view as ImageView

            var tappedCounter = counter.tag.toString().toInt()

            if (gameState[tappedCounter] == 2 && isActive) {
                counter.translationY = -1000f
                gameState[tappedCounter] = activePlayer

                if (activePlayer == 0) {

                    counter.setImageResource(R.drawable.yellow)
                    activePlayer = 1
                } else {
                    counter.setImageResource(R.drawable.red)
                    activePlayer = 0
                }

                counter.animate().apply {
                    translationYBy(1000f)
                    rotation(720f)
                    duration = 300
                }

                for (winningPosition: IntArray in winningPositions) {

                    if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                            gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                            gameState[winningPosition[0]] != 2) {
                        // Someone has won
                        isActive = false
                        var winner = "Red"

                        if (gameState[winningPosition[0]] == 0) {
                            winner = "Yellow"
                        }

                        val winnerMessage: TextView = findViewById(R.id.winnerMessage)

                        winnerMessage.text = "$winner has won!"

                        val layout: LinearLayout = findViewById(R.id.playAgainLayout)
                        layout.visibility = View.VISIBLE
                    } else {
                        val gameIsOver = !gameState.contains(2)
                        if (gameIsOver){
                            val winnerMessage: TextView = findViewById(R.id.winnerMessage)

                            winnerMessage.text = "It's a draw!"

                            val layout: LinearLayout = findViewById(R.id.playAgainLayout)
                            layout.visibility = View.VISIBLE
                        }
                    }
                }
            }
    }

    fun playAgain(view: View){
        val layout : LinearLayout = findViewById(R.id.playAgainLayout)
        layout.visibility = View.INVISIBLE
        activePlayer = 0
        gameState = intArrayOf(2,2,2,2,2,2,2,2,2)

        val gridLayout: GridLayout = findViewById(R.id.gridLayout)

        for (i in 1..gridLayout.childCount){
            val children : View = gridLayout.getChildAt(i-1)
            (children as ImageView).setImageResource(0)
        }
        isActive = true
    }

}
