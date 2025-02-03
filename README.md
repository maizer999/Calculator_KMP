### **Documentation: CalculatorViewModel and State Management**

#### **Overview**
The `CalculatorViewModel` class is designed to manage the state of a simple calculator application. It encapsulates the logic required to handle user input, perform calculations, and update the UI state in response to those actions. The `CalculatorState` class is a data class that holds the current state of the calculator, including operands, the active operation, and the current value being entered.

The `CalculatorViewModel` uses **Kotlin Flow** to handle state updates and propagate changes. It leverages `MutableStateFlow` to hold and modify the current state, and `asStateFlow()` to expose a read-only flow to external components, such as the UI.

#### **State Management**
State management is handled using **`StateFlow`**, which is a state-holder observable flow. It allows the ViewModel to manage state reactively and ensures that the UI reflects changes automatically when the state is updated.

- **`MutableStateFlow`** is a mutable state holder, which allows changes to be made to the state.
- **`StateFlow`** is an immutable version of `MutableStateFlow`, which only exposes a read-only state. This ensures that external components can observe the state but cannot modify it directly.

By updating the state with `MutableStateFlow.update()`, we ensure that the state is modified in a controlled manner and that observers of the state (such as the UI) are notified when changes occur.

---

### **Detailed Breakdown**

#### **1. CalculatorViewModel**
The `CalculatorViewModel` class is responsible for handling the business logic of the calculator, including user interactions and state management. It contains several key methods that define how the calculator behaves.

##### **State Flow Setup**

```kotlin
private val _state = MutableStateFlow(CalculatorState())
val state = _state.asStateFlow()
```

- `_state` is a private `MutableStateFlow` that holds the current state of the calculator.
- `state` is the publicly accessible, read-only version of the state (`StateFlow`) that can be observed by UI components but not modified directly.

##### **Methods to Handle User Input**

The calculator supports multiple types of user inputs: digits, operators, and special functions. Each user input is handled by a corresponding method.

1. **`onDigitClick(digit: String)`**
   This method handles the input of numeric digits. If the current operand is "0", the new digit replaces it; otherwise, it appends the digit to the existing operand.

   ```kotlin
   fun onDigitClick(digit: String) {
       if (digit.isBlank()) return // Ignore empty or blank input

       _state.update {
           val newOperand = if (it.currentOperand == "0") {
               digit // Replace "0" with the new digit
           } else {
               it.currentOperand + digit // Append digit
           }
           it.copy(currentOperand = newOperand)
       }
   }
   ```

2. **`onOperatorClick(button: String)`**
   This method handles operator input (such as addition, subtraction, etc.). Depending on the operator clicked, it either stores the operator, toggles the sign, adds a decimal point, calculates the result, or resets the calculator.

   ```kotlin
   fun onOperatorClick(button: String) {
       when (button) {
           "AC" -> resetCalculator()
           "+/-" -> toggleSign()
           "+", "-", "*", "/", "%" -> storeOperator(button)
           "." -> addDecimal()
           "=" -> calculateResult()
       }
   }
   ```

3. **`resetCalculator()`**
   Resets the calculator’s state to its initial condition by updating the state with default values for operands and operations.

   ```kotlin
   private fun resetCalculator() {
       _state.update { CalculatorState() }
   }
   ```

4. **`toggleSign()`**
   This method toggles the sign of the current operand. If the operand is positive, it becomes negative, and vice versa.

   ```kotlin
   private fun toggleSign() {
       _state.update {
           val value = it.currentOperand.toDoubleOrNull() ?: 0.0
           it.copy(currentOperand = (-value).toString())
       }
   }
   ```

5. **`storeOperator(operator: String)`**
   When an operator is clicked, this method stores the current operand as the first operand and sets the current operation.

   ```kotlin
   private fun storeOperator(operator: String) {
       _state.update {
           it.copy(
               firstOperand = it.currentOperand, // Save current value as first operand
               currentOperation = operator,      // Store the operator
               currentOperand = "0"              // Reset current input
           )
       }
   }
   ```

6. **`addDecimal()`**
   Ensures that only one decimal point is added to the current operand.

   ```kotlin
   private fun addDecimal() {
       _state.update {
           if (it.currentOperand.contains(".")) it else it.copy(currentOperand = it.currentOperand + ".")
       }
   }
   ```

7. **`calculateResult()`**
   This method performs the actual calculation based on the stored operands and operator. It handles all basic operations (`+`, `-`, `*`, `/`, `%`), and returns an error message if division by zero is attempted.

   ```kotlin
   private fun calculateResult() {
       _state.update {
           val first = it.firstOperand.toDoubleOrNull() ?: 0.0
           val second = it.currentOperand.toDoubleOrNull() ?: 0.0

           val result = when (it.currentOperation) {
               "+" -> (first + second).toString()
               "-" -> (first - second).toString()
               "*" -> (first * second).toString()
               "/" -> if (second != 0.0) (first / second).toString() else "Error"
               "%" -> (first % second).toString() // Handling modulus operation
               else -> it.currentOperand
           }
           it.copy(currentOperand = result, firstOperand = "", currentOperation = "")
       }
   }
   ```

#### **2. CalculatorState**
The `CalculatorState` is a data class that holds the state of the calculator. It stores the current operands, the current operation, and the current value being entered.

```kotlin
data class CalculatorState(
    val firstOperand: String = "0",    // The first operand in the calculation
    val secondOperand: String = "0",   // The second operand (no longer needed)
    val currentOperand: String = "0",  // The operand being worked on currently
    val currentOperation: String? = null // Current operation (e.g., +, -, *, /)
)
```

- **`firstOperand`**: Holds the first operand in the calculation.
- **`secondOperand`**: This value is no longer needed in this context and can be safely removed, as the `CalculatorViewModel` handles operand storage.
- **`currentOperand`**: The operand currently being entered or displayed on the calculator.
- **`currentOperation`**: The operator currently selected (e.g., `+`, `-`, `*`, `/`).

#### **Summary**
The `CalculatorViewModel` manages the state and logic of the calculator using a reactive state management system powered by `StateFlow`. The ViewModel is responsible for handling user input (numbers, operators), performing calculations, and updating the state accordingly. The `CalculatorState` class holds the current data necessary for the UI and business logic.

By using `StateFlow`, the ViewModel ensures that changes to the state are propagated reactively, so the UI components can observe state changes and update accordingly. The use of immutable state (through `StateFlow`) ensures that the state can only be modified within the ViewModel, maintaining a clean separation of concerns and making the state management predictable and manageable.
