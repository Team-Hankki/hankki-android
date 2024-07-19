package com.hankki.feature.my.myjogbo

import com.hankki.feature.my.myjogbo.model.MyJogboModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyJogboState(
    val editMode: Boolean = false,
    val myJogboItems: PersistentList<MyJogboModel> = persistentListOf(MyJogboModel()),
    var showDialog: Boolean = false,
)
