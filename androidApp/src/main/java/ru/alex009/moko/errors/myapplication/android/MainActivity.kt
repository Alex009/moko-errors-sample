package ru.alex009.moko.errors.myapplication.android

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.mvvm.getViewModel
import dev.icerock.moko.mvvm.livedata.bindTextTwoWay
import dev.icerock.moko.resources.desc.StringDesc
import ru.alex009.moko.errors.myapplication.LoginViewModel
import ru.alex009.moko.errors.myapplication.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoginViewModel.EventsListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: LoginViewModel = getViewModel {
            LoginViewModel(eventsDispatcherOnMain())
        }

        binding.loginText.bindTextTwoWay(this, viewModel.login)
        binding.passwordText.bindTextTwoWay(this, viewModel.password)

        binding.loginBtn.setOnClickListener { viewModel.onLoginPressed() }

        viewModel.eventsDispatcher.bind(this, this)
        viewModel.exceptionHandler.bind(
            lifecycleOwner = this,
            activity = this
        )
    }

    override fun showAlert(title: StringDesc?, message: StringDesc) {
        AlertDialog.Builder(this)
            .setTitle(title?.toString(this))
            .setMessage(message.toString(this))
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .show()
    }
}
