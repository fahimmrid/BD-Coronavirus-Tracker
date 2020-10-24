package com.example.bangladeshcovid19.model

import com.example.bangladeshcovid19.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface BDCovidAPI {

    //https://corona-bd.herokuapp.com/stats
    @GET("stats")
    fun getMestats(): Call<BdApiResponse>

    @GET("district")
    fun getMedistrict(): Call<BDtwoResponse>


    companion object {
        fun initRetrofit(): BDCovidAPI {
            return if (BuildConfig.DEBUG) {
                Retrofit.Builder()
                    .client(createClient())
                    .baseUrl("https://corona-bd.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BDCovidAPI::class.java)
            } else {
                Retrofit.Builder()
                    .baseUrl("https://corona-bd.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BDCovidAPI::class.java)
            }
        }

        private fun createClient(): OkHttpClient {
            val logger: HttpLoggingInterceptor = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
        }
    }
}