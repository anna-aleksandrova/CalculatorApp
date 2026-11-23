package com.example.calculatorapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculatorapp.databinding.ActivityFullCalculatorBinding

class FullCalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullCalculatorBinding
    private var firstNumber: Double? = null
    private var currentOp: String? = null
    private var isNewInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val digitButtons = listOf(
            binding.btn0 to "0", binding.btn1 to "1", binding.btn2 to "2",
            binding.btn3 to "3", binding.btn4 to "4", binding.btn5 to "5",
            binding.btn6 to "6", binding.btn7 to "7", binding.btn8 to "8",
            binding.btn9 to "9"
        )

        for ((btn, digit) in digitButtons) {
            btn.setOnClickListener {
                if (isNewInput || binding.tvDisplay.text == "0" || binding.tvDisplay.text == "Помилка") {
                    binding.tvDisplay.text = digit
                    isNewInput = false
                } else {
                    binding.tvDisplay.append(digit)
                }
            }
        }

        binding.btnDot.setOnClickListener {
            val current = binding.tvDisplay.text.toString()
            if (isNewInput || current == "Помилка") {
                binding.tvDisplay.text = "0."
                isNewInput = false
            } else if (!current.contains(".")) {
                binding.tvDisplay.append(".")
            }
        }

        fun onOperation(op: String) {
            val value = binding.tvDisplay.text.toString().toDoubleOrNull() ?: return
            if (firstNumber != null && currentOp != null && !isNewInput) {
                calculateResult()
                firstNumber = binding.tvDisplay.text.toString().toDoubleOrNull()
            } else {
                firstNumber = value
            }
            currentOp = op
            isNewInput = true
        }

        binding.btnAdd.setOnClickListener { onOperation("+") }
        binding.btnSub.setOnClickListener { onOperation("-") }
        binding.btnMul.setOnClickListener { onOperation("*") }
        binding.btnDiv.setOnClickListener { onOperation("/") }

        binding.btnEquals.setOnClickListener {
            calculateResult()
            currentOp = null
        }

        binding.btnC.setOnClickListener {
            binding.tvDisplay.text = "0"
            firstNumber = null
            currentOp = null
            isNewInput = true
        }

        binding.btnBack.setOnClickListener {
            val text = binding.tvDisplay.text.toString()
            if (!isNewInput && text.length > 1 && text != "Помилка") {
                binding.tvDisplay.text = text.dropLast(1)
            } else {
                binding.tvDisplay.text = "0"
                isNewInput = true
            }
        }
    }

    private fun calculateResult() {
        val a = firstNumber ?: return
        val op = currentOp ?: return
        val b = binding.tvDisplay.text.toString().toDoubleOrNull() ?: return

        if (op == "/" && b == 0.0) {
            binding.tvDisplay.text = "Помилка"
            firstNumber = null
            isNewInput = true
            return
        }

        val res = when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> a / b
            else -> b
        }
        val formatted = if (res % 1.0 == 0.0) res.toLong().toString() else res.toString()
        binding.tvDisplay.text = formatted
        firstNumber = res
        isNewInput = true
    }
}