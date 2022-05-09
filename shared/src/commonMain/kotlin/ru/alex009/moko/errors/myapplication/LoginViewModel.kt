package ru.alex009.moko.errors.myapplication

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.resources.format
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
        } catch (used: LoginUsedException) {
            eventsDispatcher.dispatchEvent { showAlert(MR.strings.login_already_used.desc()) }
        } catch (incorrect: IncorrectPasswordException) {
            eventsDispatcher.dispatchEvent { showAlert(MR.strings.incorrect_password.desc()) }
        } catch (network: NoNetworkException) {
            eventsDispatcher.dispatchEvent { showAlert(MR.strings.no_internet.desc()) }
        } catch (exc: Throwable) {
            val message = MR.strings.unknown_error
                .format(exc.message ?: exc::class.simpleName ?: "null")
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
