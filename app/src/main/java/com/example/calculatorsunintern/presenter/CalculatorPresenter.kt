package com.example.calculatorsunintern.presenter

import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorPresenter(private val viewCallback: CalculatorContract.View) :
    CalculatorContract.Presenter {

    override fun expressionIsValidated(expression: String): Boolean =
        expression.matches(Regex("^([-+]?\\d+(\\.\\d+)?)?[-+*/]?[-]?(\\d+(\\.\\d+)?)?"))


    override fun calculate(expression: String) {
        try {
            val expression2 = ExpressionBuilder(expression).build()
            viewCallback.showResult(expression2.evaluate())
        } catch (e: Exception) {
            println(e.toString())
            if (e.toString() == "java.lang.ArithmeticException: Division by zero!")
                viewCallback.onError(e.toString())
        }
    }
}
