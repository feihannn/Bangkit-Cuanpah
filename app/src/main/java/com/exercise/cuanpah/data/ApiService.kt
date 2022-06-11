package com.exercise.cuanpah.data

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
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

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String
)

data class OrderResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data:OrderResponseData
)


interface ApiService {

    @POST("/register")
    fun registerUser(@Body registerData: RegisterData ) : Call<RegisterResponse>

    @POST("/login")
    fun loginUser(@Body loginData: LoginData) : Call<LoginResponse>

    @POST("/requests")
    fun requestOrder(@Body orderData: OrderData) : Call<OrderResponse>

    @GET("/requests")
    fun getOrder(@Field("user") user:Int) : Call<OrderResponse>

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

data class OrderData(
    val userId:Int,
    val driverId:Int,
    val lat:Double,
    val lon:Double,
    val status:String
)

data class OrderResponseData(
    val userId:Int,
    val driverId:Int,
    val lat:Double,
    val lon:Double,
    val status:String,
    val requestTime:String,
    val pickupTime:String
)
