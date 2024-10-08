package com.hankki.feature.my.mypage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.hankki.core.common.utill.UiState
import com.hankki.feature.my.mypage.model.MyModel

data class MyState(
    val myModel: MyModel = MyModel(),
    val dialogSate: DialogState = DialogState.CLOSED,
    val showWebView: MutableState<String> = mutableStateOf(""),
    val uiState: UiState<MyModel> = UiState.Success(MyModel()),
)
