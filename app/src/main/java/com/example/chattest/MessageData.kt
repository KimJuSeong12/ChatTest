package com.example.chattest

data class MessageData(
    var message: String?,
    var sendId: String?
){
    constructor():this("","")
}
