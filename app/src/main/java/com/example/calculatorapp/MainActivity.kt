package com.example.calculatorapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etFirst = findViewById<EditText>(R.id.etFirstNumber)
        val etSecond = findViewById<EditText>(R.id.etSecondNumber)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        fun calculate(op: String) {
            val s1 = etFirst.text.toString().trim()
            val s2 = etSecond.text.toString().trim()
            if (s1.isEmpty() || s2.isEmpty()) {
                Log.w(TAG, "Порожні поля при операції $op")
                tvResult.text = "Будь ласка, введіть обидва числа"
                return
            }
            val a = s1.toDoubleOrNull()
            val b = s2.toDoubleOrNull()
            if (a == null || b == null) {
                Log.w(TAG, "Некоректний формат числа")
                tvResult.text = "Помилка: некоректне число"
                return
            }
            Log.d(TAG, "Операція: $a $op $b")
            if (op == "/" && b == 0.0) {
                Log.w(TAG, "Спроба ділення на нуль")
                tvResult.text = "Помилка: ділення на нуль неможливе!"
                return
            }
            val res = when (op) {
                "+" -> a + b
                "-" -> a - b
                "*" -> a * b
                "/" -> a / b
                else -> 0.0
            }
            val formatted = if (res % 1.0 == 0.0) res.toLong().toString() else res.toString()
            tvResult.text = "Результат: $formatted"
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { calculate("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { calculate("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { calculate("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { calculate("/") }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            etFirst.text.clear()
            etSecond.text.clear()
            tvResult.text = "Результат: "
            Log.d(TAG, "Поля очищено")
        }

    }
}