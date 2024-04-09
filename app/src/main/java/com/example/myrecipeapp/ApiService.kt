package com.example.myrecipeapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.themealdb.com/api/json/v1/1/")  // url(https:// 포함) 가져오고
    .addConverterFactory(GsonConverterFactory.create())  // 변환하고
    .build()

val recipeService = retrofit.create<ApiService>()  // 더 코틀린스러움
// val recipeService = retrofit.create(ApiService::class.java) // 자바와 호환이 더 잘 됨

interface ApiService {
    @GET("categories.php")
    suspend fun getCagegories(): CategoriesResponse
    // suspend fun : 비동기 작업
}