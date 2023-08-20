package com.example.recyclervideo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclervideo.adapter.MovieAdapter
import com.example.recyclervideo.api.ApiClient
import com.example.recyclervideo.api.ApiService
import com.example.recyclervideo.databinding.ActivityMainBinding
import com.example.recyclervideo.response.MovieListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val movieAdapter by lazy {
        MovieAdapter()
    }
    private val api:ApiService by lazy {
        ApiClient().getClient().create(ApiService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            proBarMovie.visibility = View.VISIBLE
            val callMovieApi = api.getPopularMovie(1)
            callMovieApi.enqueue(object : Callback<MovieListResponse>{
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    proBarMovie.visibility = View.GONE
                    when(response.code()){
                        //Phản hồi thành công
                        in 200..299->{
                            response.body().let { itBody->
                                itBody?.results.let {itData->
                                    if (itData!!.isNotEmpty()){
                                        movieAdapter.differ.submitList(itData)
                                        rvMovie.apply {
                                            layoutManager = LinearLayoutManager(this@MainActivity)
                                            adapter=movieAdapter
                                        }
                                    }
                                }
                            }
                        }
                        //Phạm vi chuyển hướng
                        in 300..399->{
                            Log.d("Phản hồi", "Phạm vi chuyển hướng : ${response.code()}")
                        }
                        //Lỗi máy khách
                        in 400..499->{
                            Log.d("Phản hồi", "Lỗi máy khách: ${response.code()}")
                        }
                        //Lỗi máy chủ
                        in 500..599->{
                            Log.d("Phản hồi","Lỗi máy chủ: ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    binding.proBarMovie.visibility=View.GONE
                    Log.e("onFailure","Error: ${t.message}")
                }

            })

        }
    }
}