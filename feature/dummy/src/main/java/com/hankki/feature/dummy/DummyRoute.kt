package com.hankki.feature.dummy

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DummyRoute(
    onSignInClick: () -> Unit,
    onShowErrorSnackBar: (Int) -> Unit,
) {
    DummyScreen()
}

@Composable
fun DummyScreen(

) {
    Column {
        Text(text = "Dummy")
    }
}
