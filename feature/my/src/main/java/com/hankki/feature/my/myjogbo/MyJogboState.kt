package com.hankki.feature.my.myjogbo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.hankki.feature.my.myjogbo.model.MyJogboModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyJogboState(
    val editMode: MutableState<Boolean> = mutableStateOf(false),
    val myJogboItems: PersistentList<MyJogboModel> = persistentListOf(
        MyJogboModel(0, "","", false)
    )
)
