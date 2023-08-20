package com.example.recyclervideo.api

import com.example.recyclervideo.utils.Constants.API_KEY
import com.example.recyclervideo.utils.Constants.BASEURL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private lateinit var retrofit:Retrofit

    private val requestIntercreptor = Interceptor{chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key",API_KEY)
            .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestIntercreptor)
        .connectTimeout(60,TimeUnit.SECONDS)
        .build()
    fun getClient():Retrofit{
        retrofit=Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

}