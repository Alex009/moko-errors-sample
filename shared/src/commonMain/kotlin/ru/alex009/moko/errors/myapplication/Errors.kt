package ru.alex009.moko.errors.myapplication

import dev.icerock.moko.resources.desc.StringDesc

class LoginUsedException : IllegalArgumentException("login already used")
class IncorrectPasswordException : IllegalArgumentException("incorrect password")
class NoNetworkException : IllegalStateException("no internet connection")

data class AlertTexts(
    val title: StringDesc,
    val message: StringDesc
)
