package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ImageButton

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var backButton: ImageButton
    private lateinit var correctanswers: TextView
    private var checktoast: Int = 0
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        backButton = findViewById(R.id.back_button)
        correctanswers = findViewById(R.id.correctanswers)
        questionTextView = findViewById(R.id.question_text_view)
        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }
        nextButton.setOnClickListener {
            nextQuest()
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
        backButton.setOnClickListener {
            backQuest()
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }
        questionTextView.setOnClickListener {
            nextQuest()
        }
        updateQuestion()

    }

    override fun onStart() {
        super.onStart()
        Log.d(
            TAG,
            "onStart() called"
        )
    }

    override fun onResume() {
        super.onResume()
        Log.d(
            TAG,
            "onResume() called"
        )
    }

    override fun onPause() {
        super.onPause()
        Log.d(
            TAG,
            "onPause() called"
        )
    }

    override fun onStop() {
        super.onStop()
        Log.d(
            TAG,
            "onStop() called"
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(
            TAG,
            "onDestroy() called"
        )
    }

    fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    fun checkAnswer(
        userAnswer:
        Boolean
    ) {

        val correctAnswer =
            questionBank[currentIndex].answer
        val messageResId = if (userAnswer ==
            correctAnswer
        ) {
            R.string.correct_toast
            checktoast + 1
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(
            this, messageResId,
            Toast.LENGTH_SHORT
        )
            .show()
    }


    fun nextQuest() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    fun backQuest() {
        currentIndex = when (currentIndex) {
            in 1..5 -> (currentIndex - 1) % questionBank.size
            else -> (currentIndex + 0) % questionBank.size
        }
        updateQuestion()
    }
}