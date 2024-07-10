package com.hankki.feature.my.myjogbo

import com.hankki.feature.my.myjogbo.model.MyJogboModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyJogboState(
    val myJogboItems: PersistentList<MyJogboModel> = persistentListOf()
)