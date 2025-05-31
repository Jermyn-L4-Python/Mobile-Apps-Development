package com.example.moodcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var expression = ""
    private var happyCount = 0
    private var sadCount = 0
    private var angryCount = 0
    private var loveCount = 0
    private var nervousCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resultText = findViewById<TextView>(R.id.textView2)

        // Mood buttons
        val happyBtn = findViewById<Button>(R.id.happy)
        val sadBtn = findViewById<Button>(R.id.sad)
        val angryBtn = findViewById<Button>(R.id.angry)
        val loveBtn = findViewById<Button>(R.id.love)
        val nervousBtn = findViewById<Button>(R.id.nervous)

        // Operators
        val addBtn = findViewById<Button>(R.id.add)
        val subBtn = findViewById<Button>(R.id.sub)
        val equalsBtn = findViewById<Button>(R.id.equals)
        val infoBtn = findViewById<Button>(R.id.info)

        // Mood emoji button clicks
        happyBtn.setOnClickListener {
            expression += "😊 "
            resultText.text = expression
        }

        sadBtn.setOnClickListener {
            expression += "☹️ "
            resultText.text = expression
        }

        angryBtn.setOnClickListener {
            expression += "😠 "
            resultText.text = expression
        }

        loveBtn.setOnClickListener {
            expression += "😍 "
            resultText.text = expression
        }

        nervousBtn.setOnClickListener {
            expression += "😬 "
            resultText.text = expression
        }

        // Operator button clicks
        addBtn.setOnClickListener {
            expression += "+ "
            resultText.text = expression
        }

        subBtn.setOnClickListener {
            expression += "- "
            resultText.text = expression
        }

        // Equals button
        equalsBtn.setOnClickListener {
            parseAndCalculateExpression()
            val moodResult = calculateMood()
            resultText.text = moodResult
            expression = "" // Clear after result
        }

        // Info button
        infoBtn.setOnClickListener {
            showInfoDialog()
        }
    }

    private fun parseAndCalculateExpression() {
        // Reset all counts
        happyCount = 0
        sadCount = 0
        angryCount = 0
        loveCount = 0
        nervousCount = 0

        val tokens = expression.trim().split(" ")
        var operator = "+"

        for (token in tokens) {
            when (token) {
                "+" -> operator = "+"
                "-" -> operator = "-"
                "😊" -> if (operator == "+") happyCount++ else happyCount--
                "☹️" -> if (operator == "+") sadCount++ else sadCount--
                "😠" -> if (operator == "+") angryCount++ else angryCount--
                "😍" -> if (operator == "+") loveCount++ else loveCount--
                "😬" -> if (operator == "+") nervousCount++ else nervousCount--
            }
        }

        // Prevent negative values
        happyCount = maxOf(0, happyCount)
        sadCount = maxOf(0, sadCount)
        angryCount = maxOf(0, angryCount)
        loveCount = maxOf(0, loveCount)
        nervousCount = maxOf(0, nervousCount)
    }

    private fun calculateMood(): String {
        val tokens = expression.trim().split(" ")
        if (tokens.size < 3) return "Please calculate 2 emojis!"

        var resultEmoji = "🤔" // default

        for (i in 0 until tokens.size - 2 step 2) {
            val mood1 = tokens[i]
            val operator = tokens[i + 1]
            val mood2 = tokens[i + 2]

            resultEmoji = when {
                mood1 == "😊" && operator == "+" && mood2 == "😍" -> "🥰"
                mood1 == "😍" && operator == "+" && mood2 == "😊" -> "🥰"
                mood1 == "😠" && operator == "+" && mood2 == "😬" -> "😵‍💫"
                mood2 == "😠" && operator == "+" && mood1 == "😬" -> "😵‍💫"
                mood1 == "😍" && operator == "+" && mood2 == "😬" -> "🤭"
                mood2 == "😍" && operator == "+" && mood1 == "😬" -> "🤭"

                mood1 == "😊" && operator == "+" && mood2 == "😊" -> "😁"
                mood1 == "😠" && operator == "+" && mood2 == "☹️" -> "😡"
                mood2 == "😠" && operator == "+" && mood1 == "☹️" -> "😡"
                mood1 == "😠" && operator == "+" && mood2 == "😠" -> "🤬"
                mood1 == "😬" && operator == "+" && mood2 == "☹️" -> "😰"
                mood2 == "😬" && operator == "+" && mood1 == "☹️" -> "😰"
                mood1 == "😬" && operator == "+" && mood2 == "😬" -> "😱"

                mood1 == "😊" && operator == "+" && mood2 == "☹️" -> "🥲"
                mood2 == "😊" && operator == "+" && mood1 == "☹️" -> "🥲"
                mood1 == "☹️" && operator == "+" && mood2 == "☹️" -> "😭"

                mood1 == "😊" && operator == "-" && mood2 == "😠" -> "😤"
                mood1 == "😠" && operator == "-" && mood2 == "😊" -> "😤"

                mood1 == "😠" && operator == "-" && mood2 == "☹️" -> "😑"
                mood2 == "😠" && operator == "-" && mood1 == "☹️" -> "😑"

                mood1 == "😊" && operator == "-" && mood2 == "☹️" -> "😐"
                mood1 == "☹️" && operator == "-" && mood2 == "😊" -> "😐"


                mood1 == "😍" && operator == "-" && mood2 == "😊" -> "😉"
                mood2 == "😍" && operator == "-" && mood1 == "😊" -> "😉"

                mood1 == "😊" && operator == "-" && mood2 == "😊" -> "😌"

                mood1 == "😍" && operator == "-" && mood2 == "😠" -> "😳"
                mood2 == "😍" && operator == "-" && mood1 == "😠" -> "😳"

                mood1 == "😍" && operator == "-" && mood2 == "😬" -> "🫣"
                mood2 == "😍" && operator == "-" && mood1 == "😬" -> "🫣"

                mood1 == "😍" && operator == "-" && mood2 == "☹️" -> "😌"
                mood2 == "😍" && operator == "-" && mood1 == "☹️" -> "😌"
                else -> "IDK 🤷"
            }
        }

        return "Result: $resultEmoji"
    }


    private fun showInfoDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("How it works")
            .setMessage(
                "Select emotions by tapping emojis.\n" +
                        "Use + and - to build an expression.\n" +
                        "Press = to calculate your overall mood.\n\n" +
                        "The mood result will appear after = and your input will reset."
            )
            .setPositiveButton("Got it!") { dialog, _ -> dialog.dismiss() }
            .create()
        dialog.show()
    }
}