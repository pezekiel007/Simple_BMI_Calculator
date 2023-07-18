package edu.aceplora.simplebmicalculator

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.truncate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val height: TextInputEditText = findViewById(R.id.etHeight)
        val weight: TextInputEditText = findViewById(R.id.etWeight)
        val bmiValue: TextView = findViewById(R.id.tvBMIValue)
        val bmiInfo: TextView = findViewById(R.id.tvBMIInfo)
        val btnCalc: Button = findViewById(R.id.btnCalc)
        val bmiCard: LinearLayout = findViewById(R.id.bmiCard)

        val underweight = getString(R.string.underweight)
        val normal = getString(R.string.normal)
        val overweight = getString(R.string.overweight)

        height.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                bmiCard.visibility = View.INVISIBLE
            }
        }
        weight.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                bmiCard.visibility = View.INVISIBLE
            }
        }

        btnCalc.setOnClickListener {
            hideKeyPad(it)

            val heightValue = height.text.toString()
            val weightValue = weight.text.toString()

            if (heightValue.isNotEmpty() && weightValue.isNotEmpty()) {

                if (heightValue != "0" && weightValue != "0") {
                    val heightInMeters = heightValue.toDouble()
                    val weightInKilograms = weightValue.toDouble()

                    var bmi = weightInKilograms / (heightInMeters * heightInMeters)
                    bmi = truncate(bmi)
                    bmiValue.text = bmi.toString()

                    when (bmi) {
                        in 0.0..0.09 -> {
                            Toast.makeText(this, "Invalid Entry", Toast.LENGTH_SHORT).show()
                            bmiCard.visibility = View.INVISIBLE
                        }

                        in 0.1..18.4 -> {
                            bmiCard.visibility = View.VISIBLE
                            bmiInfo.text = underweight
                            bmiInfo.setTextColor(Color.parseColor("#488EE2"))
                        }

                        in 18.5..25.0 -> {
                            bmiCard.visibility = View.VISIBLE
                            bmiInfo.text = normal
                            bmiInfo.setTextColor(Color.parseColor("#60B601"))
                        }

                        in 25.1..40.0 -> {
                            bmiCard.visibility = View.VISIBLE
                            bmiInfo.text = overweight
                            bmiInfo.setTextColor(Color.parseColor("#EB4F64"))
                        }
                    }
                } else {
                    Toast.makeText(this, "Invalid Entry", Toast.LENGTH_SHORT).show()
                    bmiCard.visibility = View.INVISIBLE
                }

            } else {
                bmiCard.visibility = View.INVISIBLE
                Toast.makeText(this, "Invalid Entry", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun hideKeyPad(it: View) {
        val hideKeyPad = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hideKeyPad.hideSoftInputFromWindow(it.windowToken, 0)
    }

}
