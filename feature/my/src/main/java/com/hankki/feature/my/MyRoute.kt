package com.hankki.feature.my

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MyRoute() {
    MyScreen()
}

@Composable
fun MyScreen(

) {
    Column {
        Text(text = "My")
    }
}
