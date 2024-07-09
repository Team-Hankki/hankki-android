package com.hankki.feature.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ReportRoute(
    navigateToLogin: () -> Unit,
    navigateToUniversity: () -> Unit
) {
    ReportScreen(
        navigateToLogin = navigateToLogin,
        navigateToUniversity = navigateToUniversity
    )
}

@Composable
fun ReportScreen(
    navigateToLogin: () -> Unit,
    navigateToUniversity: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Report")

        Button(onClick = navigateToLogin) {
            Text("Login")
        }

        Button(onClick = navigateToUniversity) {
            Text("University")
        }
    }
}