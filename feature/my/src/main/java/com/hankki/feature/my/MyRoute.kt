package com.hankki.feature.my

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MyRoute(navigateToDummy: () -> Unit, navigateToLogin: () -> Unit) {
    MyScreen(navigateToDummy = navigateToDummy, navigateToLogin = navigateToLogin)
}

@Composable
fun MyScreen(
    navigateToDummy: () -> Unit,
    navigateToLogin: () -> Unit
) {
    Column {
        Text(text = "My")
        Button(onClick = navigateToDummy) {
            Text(text = "Go to Dummy")
        }

        Button(onClick = navigateToLogin) {
            Text("Login")
        }
    }
}
