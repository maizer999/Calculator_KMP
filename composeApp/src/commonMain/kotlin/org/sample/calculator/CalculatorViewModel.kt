package org.sample.calculator

import android.util.Log
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.MutableStateFlow

class CalculatorViewModel {
    private val _state = MutableStateFlow(CalculatorState())
    val state = _state.asStateFlow()

    /** Handles number inputs separately */
    fun onDigitClick(digit: String) {
        if (digit == " " || digit.isEmpty()) return // Ignore empty or blank input
        Log.d("CalculatorDebug", "onDigitClick: digit = $digit")

        _state.update {
            val newOperand = if (it.currentOperand == "0") {
                digit // Replace "0" with the new digit
            } else {
                it.currentOperand + digit // Append digit
            }
            Log.d("CalculatorDebug", "New Operand: $newOperand")
            it.copy(currentOperand = newOperand)

        }
    }

    /** Handles operator inputs */
    fun onOperatorClick(button: String) {
        Log.d("CalculatorDebug", "onOperatorClick: button = $button")
        when (button) {
            "AC" -> resetCalculator()
            "+/-" -> toggleSign()
            "+", "-", "*", "/", "%" -> storeOperator(button)
            "." -> addDecimal()
            "=" -> calculateResult()
        }
    }

    /** Resets the calculator to its initial state */
    private fun resetCalculator() {
        Log.d("CalculatorDebug", "resetCalculator: Resetting calculator")
        _state.update { CalculatorState() }
    }

    /** Toggles the sign of the current operand */
    private fun toggleSign() {
        Log.d("CalculatorDebug", "toggleSign: currentOperand = ${_state.value.currentOperand}")

        _state.update {
            val value = it.currentOperand.toDoubleOrNull() ?: 0.0
            val newOperand = value.unaryMinus().toString()
            Log.d("CalculatorDebug", "New Operand after toggle: $newOperand")
            it.copy(currentOperand = newOperand)
        }
    }

    /** Stores the first operand and sets the current operator */
    private fun storeOperator(operator: String) {
        Log.d("CalculatorDebug", "storeOperator: operator = $operator")

        _state.update {
            it.copy(
                firstOperand = it.currentOperand, // Save current value as first operand
                currentOperation = operator, // Store the operator
                currentOperand = "0" // Reset current input
            )
        }
    }

    /** Ensures only one decimal point is added */
    private fun addDecimal() {
        _state.update {
            if (it.currentOperand.contains(".")) {
                it // Do nothing if decimal already exists
            } else {
                it.copy(currentOperand = it.currentOperand + ".")
            }
        }
    }

    /** Performs the calculation and updates the state */
    private fun calculateResult() {
        val first = _state.value.firstOperand.toDoubleOrNull() ?: 0.0
        val second = _state.value.currentOperand.toDoubleOrNull() ?: 0.0
        Log.d("CalculatorDebug", "calculateResult: firstOperand = $first, secondOperand = $second")

        _state.update {
            val result = when (it.currentOperation) {
                "/" -> if (second == 0.0) "Error" else (first / second).toString()
                "*" -> (first * second).toString()
                "+" -> (first + second).toString()
                "-" -> (first - second).toString()
                "%" -> (first % second).toString()
                else -> it.currentOperand
            }
            Log.d("CalculatorDebug", "Result: $result")
            it.copy(currentOperand = result, firstOperand = "", currentOperation = "")
        }
    }
}

