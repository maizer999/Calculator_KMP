package org.sample.calculator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import calculator.composeapp.generated.resources.Res
import calculator.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    val buttons = remember {
        listOf(
        listOf("AC", "+/-", "%", "/"),
        listOf("7", "8", "9",   "*"),
        listOf("4", "5", "6",   "-"),
        listOf("1", "2", "3",   "+"),
        listOf("0","   ", ".",  "=")
    ) }
    val operators     = remember { listOf("/" ,"*" ,"+" ,"-" ,"=") }
    val extraOperators = remember { listOf("AC" ,"+/-" ,"%") }
    MaterialTheme (colors = themeColors() ) {
    Column(Modifier.fillMaxWidth()){
        Spacer(modifier = Modifier.height(1.dp))
        Text(
            text = "0" ,
            modifier = Modifier
                .fillMaxWidth()
                .padding( 10.dp)
                .align(alignment = Alignment.End)
                .background(color = Color.DarkGray),
            style = TextStyle(fontSize = 70.sp) ,
            textAlign = TextAlign.End ,
            color = Color.White

        )

        buttons.forEach { rowButtons ->
            Row(
                Modifier.align(Alignment.Start).fillMaxWidth()
            ) {
                rowButtons.forEachIndexed { index , item ->
                    when {
                        extraOperators.contains(item) ->{
                            extraButton(
                                modifier = Modifier.weight(1f),
                                text = item ,
                                 onClick = {}
                            )
                        }
                        operators.contains(item) -> {
                            operatorButton(
                                modifier = Modifier.weight(1f),
                                text = item ,
                                onClick = { })
                        } else -> {

                         digitButton(
                             Modifier.weight(
                                 if( rowButtons.size< 4 && index ==0)  2f else 1f
                             ) ,
                             text = item ,
                             onClick = {} )
                        }
                    }
                }
            }
        }
    }
    }
}

@Composable
fun themeColors(): Colors {
    return Colors(
        primary = Color.Gray,
        primaryVariant = Color.DarkGray,
        secondary = Color(red = 242, green = 163, blue = 60, alpha = 255),
        secondaryVariant = Color.Yellow,
        background = Color.Black,
        surface = MaterialTheme.colors.surface,
        error = MaterialTheme.colors.error,
        onPrimary = MaterialTheme.colors.onPrimary,
        onSecondary = MaterialTheme.colors.onSecondary,
        onBackground = MaterialTheme.colors.onBackground,
        onSurface = MaterialTheme.colors.onSurface,
        onError = MaterialTheme.colors.onError,
        isLight = MaterialTheme.colors.isLight
    )
}
