package org.sample.calculator

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

data class CalculatorState(
    val firstOperand: String = "0", // The first operand in the calculation
    val secondOperand: String = "0", // The second operand
    val currentOperand: String = "0", // The operand being worked on currently
    val currentOperation: String? = null // Current operation (e.g., +, -, *, /)
) {

    // Handle reset (AC button)
    fun reset(): CalculatorState {
        return copy(firstOperand = "0", secondOperand = "0", currentOperand = "0", currentOperation = null)
    }

    // Handle number input
    fun onNumberInput(number: String): CalculatorState {
        return if (currentOperand == "0" || currentOperand == "-0") {
            copy(currentOperand = number)
        } else {
            copy(currentOperand = currentOperand + number)
        }
    }

    // Handle the operation input (like +, -, *, /)
    fun onOperationInput(operation: String): CalculatorState {
        return when {
            currentOperation == null -> {
                // If no operation is set yet, set it and store the first operand
                copy(firstOperand = currentOperand, currentOperand = "0", currentOperation = operation)
            }
            else -> {
                // If an operation is already set, perform the calculation and set the new operation
                val result = calculateResult()
                copy(firstOperand = result, currentOperand = "0", currentOperation = operation)
            }
        }
    }

    // Calculate the result of the operation
    fun calculateResult(): String {
        val first = firstOperand.toDoubleOrNull() ?: return currentOperand
        val second = currentOperand.toDoubleOrNull() ?: return currentOperand
        return when (currentOperation) {
            "+" -> (first + second).toString()
            "-" -> (first - second).toString()
            "*" -> (first * second).toString()
            "/" -> if (second != 0.0) (first / second).toString() else "Error"
            "%" -> (first % second).toString() // Handling modulus operation
            else -> currentOperand
        }
    }
}
