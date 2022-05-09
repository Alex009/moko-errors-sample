package ru.alex009.moko.errors.myapplication

import dev.icerock.moko.errors.mappers.ExceptionMappersStorage
import dev.icerock.moko.errors.mappers.mapThrowable
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import ru.alex009.moko.errors.MR

object Configurator {
    fun init() {
        ExceptionMappersStorage
            .register<LoginUsedException, StringDesc> { MR.strings.login_already_used.desc() }
            .register<IncorrectPasswordException, StringDesc> { MR.strings.incorrect_password.desc() }
            .register<NoNetworkException, StringDesc> { MR.strings.no_internet.desc() }
            .condition<AlertTexts>(condition = { it is NoNetworkException }) { exc ->
                AlertTexts(
                    title = MR.strings.network_error.desc(),
                    message = exc.mapThrowable()
                )
            }
            .condition<AlertTexts>(condition = { true }) { exc ->
                AlertTexts(
                    title = MR.strings.common_error.desc(),
                    message = exc.mapThrowable()
                )
            }
            .setFallbackValue(
                AlertTexts(
                    title = MR.strings.common_error.desc(),
                    message = MR.strings.unknown_error.desc()
                )
            )


//        ExceptionMappersStorage
//            .condition<StringDesc>(condition = { it is LoginUsedException }) { MR.strings.login_already_used.desc() }
//            .condition<StringDesc>(condition = { it is IncorrectPasswordException }) { MR.strings.incorrect_password.desc() }
//            .condition<StringDesc>(condition = { it is NoNetworkException }) { MR.strings.no_internet.desc() }
//            .condition<StringDesc>(
//                condition = { true },
//                mapper = { exc ->
//                    MR.strings.unknown_error.format(exc.message ?: exc::class.simpleName ?: "null")
//                }
//            )
    }
}
