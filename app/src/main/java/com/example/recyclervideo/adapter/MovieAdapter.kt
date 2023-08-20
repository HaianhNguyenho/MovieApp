package com.example.recyclervideo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.recyclervideo.DetailsMovieActivity
import com.example.recyclervideo.R
import com.example.recyclervideo.databinding.ItemsRowBinding
import com.example.recyclervideo.response.MovieListResponse
import com.example.recyclervideo.utils.Constants.POSTER_BASEURL

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    private lateinit var binding:ItemsRowBinding

    private lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemsRowBinding.inflate(inflater,parent, false)
        context = parent.context
        return ViewHolder()

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ViewHolder:RecyclerView.ViewHolder(binding.root){

        fun bind(item:MovieListResponse.Result){
            binding.apply {
                tenPhim.text = item.title
                tvStar.text = item.vote_average.toString()
                val moviePosterURL = POSTER_BASEURL + item.poster_path
                imgMovie.load(moviePosterURL){
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                tvLanguage.text=item.original_language
                Day.text=item.release_date

                root.setOnClickListener{
                    val intent = Intent(context, DetailsMovieActivity::class.java)
                    intent.putExtra("id", item.id)
                    context.startActivity(intent)
                }

            }

        }

    }
    private val diffeCallBack=object : DiffUtil.ItemCallback<MovieListResponse.Result>(){
        override fun areItemsTheSame(
            oldItem: MovieListResponse.Result,
            newItem: MovieListResponse.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieListResponse.Result,
            newItem: MovieListResponse.Result
        ): Boolean {
            return oldItem==newItem
        }

    }
    val differ = AsyncListDiffer(this,diffeCallBack)

}