package com.mertoenjosh.basicworkmanager

object WorkStatusSingleton {
    var workComplete: Boolean = false
    var workMessage: String = ""

    var workRetries = 0
}