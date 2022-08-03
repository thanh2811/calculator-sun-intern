package com.example.calculatorsunintern.presenter

interface CalculatorContract {

    interface View{
        fun showResult(result: Double)
        fun onError(errorMessage: String)
    }

    interface Presenter{
        fun expressionIsValidated(expression: String): Boolean
        fun calculate(expression: String)
    }

}