package com.exercise.cuanpah.data

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
    @FormUrlEncoded
    @POST("/register")
    fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

}