package ru.alex009.moko.errors.myapplication

import dev.icerock.moko.errors.mappers.mapThrowable
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import ru.alex009.moko.errors.MR

class LoginViewModel(
    val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel() {
    val login: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")

    fun onLoginPressed() {
        try {
            authorize(login = login.value, password = password.value)
            eventsDispatcher.dispatchEvent { showAlert(MR.strings.login_success.desc()) }
        } catch (exc: Throwable) {
            val message: StringDesc = exc.mapThrowable()
            eventsDispatcher.dispatchEvent { showAlert(message) }
        }
    }

    private fun authorize(login: String, password: String) {
        if (login == "used") throw LoginUsedException()
        if (login == "unknown") throw IllegalStateException("can't auth now")
        if (login == "unknown2") throw IllegalStateException()
        if (login == "unknown3") throw object : Exception() {}
        if (login == "network") throw NoNetworkException()
        if (password == "incorrect") throw IncorrectPasswordException()

        // success :)
    }

    interface EventsListener {
        fun showAlert(message: StringDesc)
    }
}

class LoginUsedException : IllegalArgumentException("login already used")
class IncorrectPasswordException : IllegalArgumentException("incorrect password")
class NoNetworkException : IllegalStateException("no internet connection")
