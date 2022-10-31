package com.arief.moviedb.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.arief.moviedb.R
import com.arief.moviedb.databinding.RvMoviesBinding
import com.arief.moviedb.model.Genres
import com.arief.moviedb.model.Movies
import com.arief.moviedb.ui.MainActivity
import com.bumptech.glide.Glide

/**
 *
 * This adapter use for displaying list of movies </br>
 * Displaying Movie Poster, Title, Rating, Overview, Genres and Favorite Button </br>
 *
 * @property items is List of Movies Data
 */
class MovieAdapter(private var items: List<Movies>, private val fragment: Fragment) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var genres: List<Genres> = listOf()
    private var favorite: MutableList<Movies> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvMoviesBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: RvMoviesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movies) {
            if(!item.poster_path.isNullOrEmpty()) {
                Glide.with(fragment)
                    .load(fragment.getString(R.string.image_url) + item.poster_path)
                    .error(R.drawable.no_image)
                    .into(binding.poster)
            }
            binding.title.text = item.title
            binding.rating.text = item.vote_average.toString()
            binding.overview.text = limitOverview(item.overview)
            binding.genres.text = convertGenres(item.genre_ids)
            if(favorite.any { it.id == item.id }){
                binding.favorite.setImageResource(R.drawable.ic_favorite_32)
            }else{
                binding.favorite.setImageResource(R.drawable.ic_favorite_border_32)
            }
            binding.favorite.setOnClickListener {
                if(favorite.any { it.id == item.id }){
                    deleteFavorite(item)
                }else{
                    addFavorite(item)
                }
            }
            binding.mainCard.setOnClickListener {
                val bundle = bundleOf("movies" to item)
                fragment.findNavController().navigate(R.id.detailFragment, bundle)
            }
            binding.executePendingBindings()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData(){
        this.items = listOf()
        notifyDataSetChanged()
    }

    fun setData(data : List<Movies>){
        this.items = data
    }

    fun addData(data : List<Movies>){
        val lastPosition = this.items.size
        this.items += data
        notifyItemRangeInserted(lastPosition, this.items.size-1)
    }

    fun setGenre(data : List<Genres>){
        this.genres = data
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFavorite(data : List<Movies>){
        this.favorite = data as MutableList<Movies>
        notifyDataSetChanged()
    }

    fun addFavorite(data: Movies){
        (fragment.requireActivity() as MainActivity).insertFavorite(data)
    }

    fun deleteFavorite(data: Movies) {
        (fragment.requireActivity() as MainActivity).deleteFavorite(data)
    }

    /**
     * Limiting the maximum of overview chars to 100
     */
    fun limitOverview(data: String): String{
        return if(data.length > 100) {
            var overview = data.substring(0, 100)
            if (overview[overview.length - 1] != '.') {
                overview = "$overview..."
            }
            overview
        }else{
            data
        }
    }

    /**
     * List of Genres displaying with code, this function converting from that code to Genre Name
     */
    fun convertGenres(data: List<Int>): String{
        var genres = ""
        return if(data.isNotEmpty()) {
            data.forEach {
                val item = this.genres.filter { x -> x.id == it }
                if (item.isNotEmpty()) {
                    genres += "${item[0].name}, "
                }
            }
            if(genres.length > 2) {
                genres.substring(0, genres.length-2)
            }else{
                genres
            }
        }else{
            genres
        }
    }
}