package ru.alex009.moko.errors.myapplication

import dev.icerock.moko.errors.handler.ExceptionHandler
import dev.icerock.moko.errors.mappers.mapThrowable
import dev.icerock.moko.errors.presenters.AlertErrorPresenter
import dev.icerock.moko.errors.presenters.SelectorErrorPresenter
import dev.icerock.moko.errors.presenters.ToastErrorPresenter
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.launch
import ru.alex009.moko.errors.MR

class LoginViewModel(
    val eventsDispatcher: EventsDispatcher<EventsListener>,
    val exceptionHandler: ExceptionHandler
) : ViewModel() {

    constructor(
        eventsDispatcher: EventsDispatcher<EventsListener>
    ) : this(
        eventsDispatcher = eventsDispatcher,
        exceptionHandler = ExceptionHandler(
            exceptionMapper = { it.mapThrowable() },
            errorPresenter = SelectorErrorPresenter(
                errorPresenterSelector = { throwable ->
                    if (throwable is IncorrectPasswordException) ToastErrorPresenter()
                    else AlertErrorPresenter()
                }
            )
        )
    )

    val login: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")

    fun onLoginPressed() {
        viewModelScope.launch {
            exceptionHandler.handle {
                authorize(login = login.value, password = password.value)
                eventsDispatcher.dispatchEvent {
                    showAlert(
                        title = null,
                        message = MR.strings.login_success.desc()
                    )
                }
            }.execute()
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
        fun showAlert(title: StringDesc?, message: StringDesc)
    }
}
