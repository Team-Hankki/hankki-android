package com.hankki.feature.my.mypage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.hankki.feature.my.mypage.model.MyModel

data class MyState(
    val myModel: MyModel = MyModel(),
    val showDialog : MutableState<String> = mutableStateOf(""),
    val showWebView : MutableState<String> = mutableStateOf("")
)
