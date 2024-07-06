package com.hankki.feature.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ReportRoute(paddingValues: PaddingValues, navigateToLogin: () -> Unit) {
    ReportScreen(paddingValues, navigateToLogin = navigateToLogin)
}

@Composable
fun ReportScreen(
    paddingValues: PaddingValues,
    navigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Text(text = "Report")

        Button(onClick = navigateToLogin) {
            Text("Login")
        }
    }
}