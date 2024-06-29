package com.hankki.feature.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ReportRoute(paddingValues: PaddingValues) {
    ReportScreen(paddingValues)
}

@Composable
fun ReportScreen(
    paddingValues: PaddingValues,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        Text(text = "Report")
    }
}
