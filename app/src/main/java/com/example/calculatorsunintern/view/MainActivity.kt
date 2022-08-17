package com.example.calculatorsunintern.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.calculatorsunintern.R
import com.example.calculatorsunintern.presenter.CalculatorContract
import com.example.calculatorsunintern.presenter.CalculatorPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewCallback = object : CalculatorContract.View {
        override fun showResult(result: Double) {
            tv_result.text = result.toString()
        }

        override fun onError(errorMessage: String) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setNegativeButton("Close", null)
                .create()
                .show()
        }
    }
    private lateinit var operationBtn: MutableList<Button>
    private lateinit var operatorBtn: MutableList<Button>

    private lateinit var calculatorPresenter: CalculatorPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calculatorPresenter = CalculatorPresenter(viewCallback)

        operationBtn = mutableListOf(
            bt_0, bt_1, bt_2, bt_3, bt_4, bt_5, bt_6, bt_7, bt_8, bt_9, bt_dot
        )
        operatorBtn = mutableListOf(
            bt_plus, bt_sub, bt_div, bt_mul, bt_div_remainder
        )

        initListener()
    }

    @SuppressLint("SetTextI18n")
    private fun initListener() {
        var currentExpression: String
        bt_backspace.setOnClickListener {
            currentExpression = et_expression.text.toString()
            et_expression.text =
                Editable.Factory.getInstance().newEditable(currentExpression.dropLast(1))
        }

        bt_clear.setOnClickListener {
            et_expression.text = Editable.Factory.getInstance().newEditable("")
            tv_result.text = ""
        }
        operationBtn.forEach { opBtn ->
            opBtn.setOnClickListener {
                currentExpression = et_expression.text.toString()
                et_expression.text =
                    Editable.Factory.getInstance().newEditable(currentExpression + opBtn.text)
            }
        }
        operatorBtn.forEach { opBtn ->
            opBtn.setOnClickListener {
                currentExpression = et_expression.text.toString()
                val newExpression = currentExpression + opBtn.text
                if (calculatorPresenter.expressionIsValidated(newExpression))
                    et_expression.text = Editable.Factory.getInstance().newEditable(newExpression)
            }
        }

        et_expression.doOnTextChanged { expression, _, _, _ ->
            // invoke calculate function
            calculatorPresenter.calculate(expression.toString())
        }
        bt_result.setOnClickListener {
            currentExpression = et_expression.text.toString()
            calculatorPresenter.calculate(currentExpression)
        }
    }

}
