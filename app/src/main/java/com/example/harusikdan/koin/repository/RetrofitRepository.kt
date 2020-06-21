package com.example.harusikdan.koin.repository

import retrofit2.Retrofit

interface AccessRetrofitRepository {
    fun getAccessRetrofit(): Retrofit
}