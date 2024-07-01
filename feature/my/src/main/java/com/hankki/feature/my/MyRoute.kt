package com.hankki.feature.my

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyRoute(paddingValues: PaddingValues,  navigateToDummy: () -> Unit) {
    MyScreen(paddingValues, navigateToDummy = navigateToDummy)
}

@Composable
fun MyScreen(
    paddingValues: PaddingValues,
    navigateToDummy: () -> Unit,
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        Text(text = "My")
        Button(onClick = navigateToDummy) {
            Text(text = "Go to Dummy")
        }
    }
}
