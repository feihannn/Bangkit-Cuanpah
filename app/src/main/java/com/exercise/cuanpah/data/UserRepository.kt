package com.exercise.cuanpah.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val apiService: ApiService,
    private val token: String,
    private val pref: UserPreference
) {

    private val message = MutableLiveData<String?>()

    // preference
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun login(user: UserModel) {
        CoroutineScope(Dispatchers.IO).launch {
            pref.login(user)
        }
    }

    fun saveUser(user: UserModel) {
        CoroutineScope(Dispatchers.IO).launch {
            pref.saveUser(user)
        }
    }

    fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            pref.logout()
        }
    }

    // func
    fun loginUser(email: String, password: String): LiveData<String?> {
        apiService.loginUser(LoginData(email, password)).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        login(UserModel(responseBody.name, responseBody.email, "", true, responseBody.token))
                        message.value = response.code().toString()
                        Log.e("Login", responseBody.message)
                    }
                } else {
                    message.value = response.code().toString()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                message.value = null
                Log.e("Register", "GAGAL")
            }

        })
        return message
    }

    fun registerUser(email: String, password: String, name: String): LiveData<String?> {
        apiService.registerUser(RegisterData(email, password, name)).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        saveUser(UserModel(name, email, password, false, ""))
                        message.value = response.code().toString()
                        Log.e("Register", responseBody.message)
                    }
                } else {
                    message.value = response.code().toString()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                message.value = null
                Log.e("Register", "GAGAL")
            }

        })
        return message
    }


}