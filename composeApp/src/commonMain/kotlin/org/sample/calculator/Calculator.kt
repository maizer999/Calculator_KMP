package org.sample.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun Calculator() {
    val buttons = remember {
        listOf(
            listOf("AC", "+/-", "%", "/"),
            listOf("7", "8", "9", "*"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", "   ", ".", "=")
        )
    }
    val operators = remember { listOf("/", "*", "+", "-", "=") }
    val extraOperators = remember { listOf("AC", "+/-", "%") }
val viewModel = remember { CalculatorViewModel() }
    val state = viewModel.state
    MaterialTheme(colors = themeColors()) {
        Column(Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = state.value.currentOperand ,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.End)
                    .background(color = Color.DarkGray),
                style = TextStyle(fontSize = 70.sp),
                textAlign = TextAlign.End,
                color = Color.White
            )

            buttons.forEach { rowButtons ->
                Row(Modifier.align(Alignment.Start).fillMaxWidth()) {
                    rowButtons.forEachIndexed { index, item ->
                        when {
                            extraOperators.contains(item) -> {
                                extraButton(
                                    modifier = Modifier.weight(1f),
                                    text = item,
                                    onClick = viewModel::onOperatorClick
                                )
                            }
                            operators.contains(item) -> {
                                operatorButton(
                                    modifier = Modifier.weight(1f),
                                    text = item,
                                    onClick = viewModel::onOperatorClick
                                )
                            }
                            else -> {
                                digitButton(
                                    Modifier.weight(
                                        if (rowButtons.size < 4 && index == 0) 2f else 1f
                                    ),
                                    text = item,
                                    onClick = {}
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

