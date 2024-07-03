package com.hankki.feature.my

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MyRoute(navigateToDummy: () -> Unit) {
    MyScreen(navigateToDummy = navigateToDummy)
}

@Composable
fun MyScreen(
    navigateToDummy: () -> Unit
) {
    Column {
        Text(text = "My")

        Button(onClick = navigateToDummy) {
            Text(text = "Go to Dummy")
        }
    }
}
