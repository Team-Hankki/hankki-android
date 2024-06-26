package com.hankki.feature.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ReportRoute() {
    ReportScreen()
}

@Composable
fun ReportScreen(

) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Report")
    }
}
