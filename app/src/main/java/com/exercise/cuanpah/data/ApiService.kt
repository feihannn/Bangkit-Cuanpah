package com.exercise.cuanpah.data

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class RegisterResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String
)

data class LoginResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String
)


interface ApiService {

    @POST("/register")
    fun registerUser(@Body registerData: RegisterData ) : Call<RegisterResponse>

    @POST("/login")
    fun loginUser(@Body loginData: LoginData) : Call<LoginResponse>

}

data class RegisterData(
    val email: String,
    val password: String,
    val name: String
)

data class LoginData(
    val email: String,
    val password: String
)
