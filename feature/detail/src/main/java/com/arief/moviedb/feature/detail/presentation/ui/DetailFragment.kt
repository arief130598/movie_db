package com.arief.moviedb.feature.detail.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arief.moviedb.R
import com.arief.moviedb.databinding.FragmentDetailBinding
import com.arief.moviedb.core.utils.Status
import com.arief.moviedb.domain.model.Movies
import com.arief.moviedb.feature.detail.presentation.adapter.SimilarAdapter
import com.arief.moviedb.feature.detail.presentation.viewmodel.DetailViewModel
import com.arief.moviedb.presentation.ui.MainActivity
import com.arief.moviedb.presentation.viewmodel.MovieViewModel
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {


    private lateinit var binding: FragmentDetailBinding
    private val viewModel : DetailViewModel by viewModels()
    private val viewModelMovie : MovieViewModel by viewModels()
    private lateinit var adapter: SimilarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SimilarAdapter(listOf(), this@DetailFragment)
        binding.rvData.adapter = adapter
        binding.rvData.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        setupUI(DetailFragmentArgs.fromBundle(requireArguments()).movies)
        observer()
    }

    fun setupUI(item: Movies){
        if(!item.backdrop_path.isNullOrEmpty()) {
            Glide.with(this)
                .load(this.getString(R.string.background_image_url) + item.backdrop_path)
                .error(R.drawable.no_image)
                .into(binding.backgroundPoster)
        }
        if(!item.poster_path.isNullOrEmpty()) {
            Glide.with(this)
                .load(this.getString(R.string.image_url) + item.poster_path)
                .error(R.drawable.no_image)
                .into(binding.poster)
        }
        binding.title.text = item.title
        binding.release.text = item.release_date
        binding.popularity.text = item.popularity.toString()
        binding.rating.text = item.vote_average.toString()
        binding.ratingTotal.text = item.vote_count.toString()
        binding.language.text = item.original_language
        binding.overview.text = item.overview
        binding.genres.text = "Genres : ${convertGenres(item.genre_ids)}"

        if(viewModelMovie.favorite.value!!.any { it.id == item.id }){
            binding.favorite.setImageResource(R.drawable.ic_favorite_32)
        }else{
            binding.favorite.setImageResource(R.drawable.ic_favorite_border_32)
        }
        binding.favorite.setOnClickListener {
            if(viewModelMovie.favorite.value!!.any { it.id == item.id }){
                binding.favorite.setImageResource(R.drawable.ic_favorite_border_32)
                (requireActivity() as MainActivity).deleteFavorite(item)
            }else{
                binding.favorite.setImageResource(R.drawable.ic_favorite_32)
                (requireActivity() as MainActivity).insertFavorite(item)
            }
        }

        viewModel.getSimilar(item.id)
    }

    private fun convertGenres(data: List<Int>): String{
        var genres = ""
        return if(data.isNotEmpty()) {
            data.forEach {
                val item = viewModelMovie.genres.value!!.filter { x -> x.id == it }
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

    private fun observer(){
        viewModel.movies.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.mainShimmer.apply {
                        stopShimmer()
                        visibility = View.GONE
                        binding.rvData.visibility = View.VISIBLE
                        adapter.setData(it.data!!)
                    }
                }
                Status.LOADING -> {
                    binding.mainShimmer.apply {
                        binding.rvData.visibility = View.GONE
                        startShimmer()
                        visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    binding.mainShimmer.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).binding.topAppBar.visibility = View.VISIBLE
        (requireActivity() as MainActivity).binding.topAppBar.menu.findItem(R.id.favoriteFragment).isVisible = false
        (requireActivity() as MainActivity).binding.topAppBar.menu.findItem(R.id.searchFragment).isVisible = false
        (requireActivity() as MainActivity).binding.navBottom.visibility = View.GONE
    }

    override fun onDetach() {
        super.onDetach()
        (requireActivity() as MainActivity).binding.topAppBar.menu.findItem(R.id.favoriteFragment).isVisible = true
        (requireActivity() as MainActivity).binding.topAppBar.menu.findItem(R.id.searchFragment).isVisible = true
        (requireActivity() as MainActivity).binding.navBottom.visibility = View.VISIBLE
    }
}