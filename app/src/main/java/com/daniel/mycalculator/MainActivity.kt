package com.daniel.mycalculator
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.lang.Exception
/*generates synthetic properties for views in xml layout, u can easily access views without having to call findViewById().*/
import kotlinx.android.synthetic.main.activity_main.*
/*imports a class from one library which is used to parse and evaluate mathematical expressions*/
import net.objecthunter.exp4j.ExpressionBuilder
class MainActivity : AppCompatActivity() {
    /*companion object is defined inside a class and can have its own properties and methods*/
    companion object {
        /*keys will be used to save the values of the placeholder and answer text views when the orientation of the device changes
        the const keyword is used to indicate that these values are constants and can't be changed.*/
        const val PLACEHOLDER_KEY = "PLACEHOLDER_KEY"
        const val ANSWER_KEY = "ANSWER_KEY"
    }

    /*saves the current state of the placeholder and answer text fields in the bundle*/
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PLACEHOLDER_KEY, placeholder.text.toString())
        outState.putString(ANSWER_KEY, answer.text.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*restores the value of the placeholder and answer from the savedInstanceState bundle*/
        savedInstanceState?.let {
            placeholder.text = it.getString(PLACEHOLDER_KEY)
            answer.text = it.getString(ANSWER_KEY)
        }

        /*sets a click that creates an intent to start the AboutActivity and switches to that activity when the button is clicked*/
        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        /*set click listeners for the number buttons and mathematical operator buttons,
        when a number button is clicked, the "numberPressed()" function is called*/
        num0.setOnClickListener { numberPressed("0", false) }
        num1.setOnClickListener { numberPressed("1", false) }
        num2.setOnClickListener { numberPressed("2", false) }
        num3.setOnClickListener { numberPressed("3", false) }
        num4.setOnClickListener { numberPressed("4", false) }
        num5.setOnClickListener { numberPressed("5", false) }
        num6.setOnClickListener { numberPressed("6", false) }
        num7.setOnClickListener { numberPressed("7", false) }
        num8.setOnClickListener { numberPressed("8", false) }
        num9.setOnClickListener { numberPressed("9", false) }
        numDot.setOnClickListener { numberPressed(".", false) }
        clear.setOnClickListener { numberPressed("", true) }
        actionDivide.setOnClickListener { numberPressed(" / ", false) }
        actionMultiply.setOnClickListener { numberPressed(" * ", false) }
        actionMinus.setOnClickListener { numberPressed(" - ", false) }
        actionAdd.setOnClickListener { numberPressed(" + ", false) }

        /*defines a click listener that removes the last character of the text in the placeholder if it's not empty.*/
        actionBack.setOnClickListener {
            val expression = placeholder.text.toString()
            if (expression.isNotEmpty()) {
                placeholder.text = expression.substring(0, expression.length - 1)
            }
        }

        /*defines a button that attempts to evaluate the mathematical expression entered in the placeholder
        if the evaluation succeeds, the result is displayed in the answer, if the result is an integer, it's
        displayed as a long integer, otherwise it is displayed as a double*/
        actionEquals.setOnClickListener {
            try {
                val expression = ExpressionBuilder(placeholder.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if (result == longResult.toDouble()) {
                    answer.text = longResult.toString()
                } else
                    answer.text = result.toString()
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    /*function that is used to process button presses from the calculator's numeric and operator buttons.*/
    private fun numberPressed(string: String, isClear: Boolean) {
        if (isClear) {
            placeholder.text = ""
            answer.text = ""
        } else {
            placeholder.append(string)
        }
    }
}