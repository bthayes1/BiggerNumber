package com.example.biggernumber

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val left: Button = findViewById(R.id.leftButton)
        val right: Button = findViewById(R.id.rightButton)
        val reset: Button = findViewById(R.id.reset)
        val background: ConstraintLayout = findViewById(R.id.background)
        var negativeTest = false
        var leftNum = 0
        var rightNum = 0
        toggleClickable(left, false)
        toggleClickable(right, false)

        reset.setOnClickListener {
            background.setBackgroundColor(Color.WHITE)
            reset.text = getString(R.string.playAgain)
            val pair = selectNewNumbers()
            leftNum = pair.first
            rightNum = pair.second
            val remainder = leftNum - rightNum
            negativeTest = remainder < 0
            gameState(reset, left, right, true)
        }

        left.setOnClickListener {
            checkAns(true, negativeTest, background, reset, left, right, leftNum, rightNum)
        }

        right.setOnClickListener {
            checkAns(false, negativeTest, background, reset, left, right, leftNum, rightNum)
        }
    }
    // When game state is true, the user is able to guess bigger #
    // when game state is false, user must press RESET("Play Again")
    private fun gameState(reset : Button, left: Button, right: Button, state: Boolean) = when {
        state -> {
            toggleClickable(reset, false)
            toggleClickable(left, true)
            toggleClickable(right, true)
            left.text = ""
            right.text = ""
        }
        else -> {
            toggleClickable(reset, true)
            toggleClickable(left, false)
            toggleClickable(right, false)
        }
    }

    // Will toggle button characteristics to make it usable/unusable
    private fun toggleClickable(button: Button, state : Boolean){
        button.isClickable = state
        button.isEnabled = state
        if(state){
            button.setBackgroundColor(Color.BLUE)
        } else{
            button.setBackgroundColor(Color.LTGRAY)
        }
    }

    // Will determine if correct answer was selected and give feedback to user
    private fun checkAns(isLeftButton: Boolean, isNegative : Boolean, background: ConstraintLayout, reset: Button, left: Button, right: Button, leftNum: Int, rightNum : Int){
        if ((isLeftButton && !isNegative) || (!isLeftButton && isNegative)){
            background.setBackgroundColor(Color.GREEN)
            Toast.makeText(this, "Congrats you won!!!", Toast.LENGTH_SHORT).show()
        }
        else{
            background.setBackgroundColor(Color.RED)
            Toast.makeText(this, "you suck...", Toast.LENGTH_SHORT).show()
        }
        gameState(reset, left, right, false)
        left.text = leftNum.toString()
        right.text = rightNum.toString()
    }
    private fun selectNewNumbers(): Pair<Int, Int> {
        val leftNum = (0..20).random()
        var rightNum = leftNum
        while(leftNum == rightNum) {
            rightNum = (0..20).random()
        }
        return Pair(leftNum, rightNum)
    }
}
