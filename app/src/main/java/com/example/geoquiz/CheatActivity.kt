package com.example.geoquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ImageButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders

const val EXTRA_ANSWER_SHOW = "AnsverForMainActivity"
private const val CHEATER = "cheater"
private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private var answerIsTrue = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        var isCheater = false
        savedInstanceState?.getBoolean(CHEATER, false)
        answerIsTrue =
            intent.getBooleanExtra(
                EXTRA_ANSWER_IS_TRUE,
                false
            )
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue ->
                    R.string.true_button
                else -> R.string.false_button

            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
            isCheater = true

        }
    }

    private fun setAnswerShownResult(isAnswerSown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOW, isAnswerSown)
        }
        setResult(RESULT_OK, data)
    }

    companion object {
        fun newIntent(
            packageContext: Context,
            answerIsTrue: Boolean
        ): Intent {
            return Intent(
                packageContext,
                CheatActivity::class.java
            ).apply {
                putExtra(
                    EXTRA_ANSWER_IS_TRUE,
                    answerIsTrue
                )
            }
        }
    }

}

