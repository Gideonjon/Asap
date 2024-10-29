package com.example.asap.utils

data class SendMoney(
    var bankName : String = "",
    var accountNumber : String = "",
    var amount : String = "",
    var reason : String = ""
)
