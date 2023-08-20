package com.example.recyclervideo.api

import android.graphics.pdf.PdfDocument.Page
import com.example.recyclervideo.response.DetailesMovieResponse
import com.example.recyclervideo.response.MovieListResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService  {
    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int):Call<MovieListResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetaies(@Path("movie_id")id:Int):Call<DetailesMovieResponse>
}