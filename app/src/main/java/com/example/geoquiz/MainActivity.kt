package com.example.geoquiz


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ImageButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var backButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var cheat: TextView
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        val currentIndex =
            savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex
        // три строчки сверху позволяет сохранить модель активити и передать ее при повороте экрана
        cheat = findViewById(R.id.cheat)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        backButton = findViewById(R.id.back_button)
        questionTextView = findViewById(R.id.question_text_view)
        cheatButton = findViewById(R.id.cheat_button)
        val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val callbackValue = result.data?.getBooleanExtra("AnsverForMainActivity", false)
                    if (callbackValue == true) cheat.setText("Жулик!!!")
                    cheat.visibility = (View.VISIBLE)
                }
            }
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
        cheatButton.setOnClickListener {
// Начало CheatActivity
            val answerIsTrue =
                quizViewModel.currentQuestionAnswer
            val intent =
                CheatActivity.newIntent(
                    this@MainActivity,
                    answerIsTrue
                )
            launcher.launch(intent)
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
        val d = Log.d(
            TAG,
            "onPause() called"
        )
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
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
    private fun updateQuestion() {
        Log.d(TAG, "Updating question text", Exception())
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
    fun checkAnswer(
        userAnswer:
        Boolean
    ) {
        val correctAnswer: Boolean =
            quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater ->
                R.string.judgment_toast
            userAnswer == correctAnswer ->
                R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(
            this, messageResId,
            Toast.LENGTH_SHORT
        )
            .show()
    }
    fun nextQuest() {
        quizViewModel.moveToNext()
        updateQuestion()
    }
    fun backQuest() {
        quizViewModel.backToNext()
        updateQuestion()
    }

}