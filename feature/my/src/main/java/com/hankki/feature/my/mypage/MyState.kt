package com.hankki.feature.my.mypage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.hankki.feature.my.mypage.model.MyModel

data class MyState(
    val myModel: MyModel = MyModel(),
    val showDialog : MutableState<Boolean> = mutableStateOf(false),
    val showWebView : MutableState<String> = mutableStateOf("")
)
