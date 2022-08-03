package com.example.calculatorsunintern.presenter

import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorPresenter(private val viewCallback: CalculatorContract.View) :
    CalculatorContract.Presenter {

    override fun expressionIsValidated(expression: String): Boolean{
        return true
    }


    override fun calculate(expression: String) {

    }
}