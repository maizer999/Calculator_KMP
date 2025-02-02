package org.sample.calculator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun extraButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (String) -> Unit
) {
    flatButton(
        modifier = modifier,
        text = text,
        contentColor = Color.White,
        backgroundColor = Color.DarkGray,
        onClick = { onClick(text) }
    )
}

@Composable
fun digitButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (String) -> Unit
) {
    flatButton(
        modifier = modifier,
        text = text,
        contentColor = Color.White,
        backgroundColor = Color.Gray,
        onClick = { onClick(text) }
    )
}

@Composable
fun operatorButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (String) -> Unit
) {
    flatButton(
        modifier = modifier,
        text = text,
        contentColor = Color.White,
        backgroundColor = Color.Yellow,
        onClick = { onClick(text) }
    )
}

@Composable
fun flatButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    text: String,
    contentColor: Color = Color.White,
    onClick: (String) -> Unit
) {
    Button(
        modifier = modifier
            .heightIn(80.dp)
            .padding(1.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor,
            backgroundColor = backgroundColor
        ),
        onClick = { onClick(text) }
    ) {
        Text(
            text = text,
            style = TextStyle(fontSize = 30.sp),
     //       modifier = Modifier.fillMaxSize()
        )
    }
}
