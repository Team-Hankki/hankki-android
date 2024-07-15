package com.hankki.feature.my.mystore

import com.hankki.feature.my.mystore.model.MyStoreModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyStoreState (
    val myStoreItems : PersistentList<MyStoreModel> = persistentListOf(MyStoreModel("",0,0,"",false,0,""))
)
