package com.exercise.cuanpah.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.exercise.cuanpah.data.UserPreference
import com.exercise.cuanpah.di.Injection
import com.exercise.cuanpah.ui.login.LoginViewModel
import com.exercise.cuanpah.ui.main.MainViewModel
import com.exercise.cuanpah.ui.register.RegisterViewModel

class ViewModelFactory(private val pref: UserPreference, private val token: String) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(Injection.provideRepository(token, pref)) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(Injection.provideRepository(token, pref)) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.provideRepository(token, pref)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}