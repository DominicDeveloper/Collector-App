package com.asadbek.readsmscalllogcontacts.models

data class Messages(
    val sendToTheNumber:String,
    val message:String,
    val read:String,
    val dataTime:String,
    var inbox:String
)
