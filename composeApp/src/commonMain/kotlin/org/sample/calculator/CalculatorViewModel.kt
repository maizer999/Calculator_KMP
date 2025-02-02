package org.sample.calculator

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel {
    private val _state = MutableStateFlow(CalculatorState())
    val state = _state.asStateFlow()

    fun onOperatorClick(button: String) {
        when (button) {
            "AC" -> {
                // Reset the state to its initial values
                _state.update { CalculatorState() }
            }
            "+/-" -> {
                unaryMinus()
            }
            "+", "-", "*", "/", "%" -> {
                // Store the first operand and set the operation
                _state.update {
                    it.copy(
                        firstOperand = it.currentOperand, // Store current operand as first operand
                        currentOperation = button, // Store the operator
                        currentOperand = "0" // Reset current operand for the next input
                    )
                }
            }
            "." -> {
                addDecimal()
            }
            "=" -> {
                // Perform the calculation and update the current operand with the result
                calculateResult()
            }
            else -> {
                // Handle number input
                _state.update {
                    it.copy(currentOperand = "${it.currentOperand.trimStart('0')}$button")
                }
            }
        }
    }

    private fun addDecimal() {
        // Add a decimal point to the current operand if it's not already present
        _state.update {
            it.copy(
                currentOperand = "${it.currentOperand.trimEnd('.')}" + "."
            )
        }
    }

    private fun unaryMinus() {
        // Toggle the sign of the current operand
        val value = _state.value.currentOperand.toDoubleOrNull() ?: 0.0
        _state.update {
            it.copy(currentOperand = value.unaryMinus().toString())
        }
    }

    private fun calculateResult() {
        // Get the first operand, second operand, and the operation
        val first = _state.value.firstOperand.toDoubleOrNull() ?: 0.0
        val second = _state.value.currentOperand.toDoubleOrNull() ?: 0.0

        // Perform the calculation based on the current operation
        _state.update {
            when (it.currentOperation) {
                "/" -> it.copy(currentOperand = (first / second).toString())
                "*" -> it.copy(currentOperand = (first * second).toString())
                "+" -> it.copy(currentOperand = (first + second).toString())
                "-" -> it.copy(currentOperand = (first - second).toString())
                "%" -> it.copy(currentOperand = (first % second).toString())
                else -> it
            }
        }
    }
}
