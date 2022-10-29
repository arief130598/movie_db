package com.arief.moviedb.ui.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.arief.moviedb.R
import com.arief.moviedb.adapter.MovieAdapter
import com.arief.moviedb.databinding.FragmentFavoriteBinding
import com.arief.moviedb.model.Movies
import com.arief.moviedb.ui.MainActivity
import com.arief.moviedb.ui.movie.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModelMovie : MovieViewModel by activityViewModel()
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorite,
            container,
            false
        )
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).binding.topAppBar.menu.findItem(R.id.favoriteFragment).isVisible = false
        (requireActivity() as MainActivity).binding.navBottom.visibility = View.INVISIBLE
    }

    override fun onDetach() {
        super.onDetach()
        (requireActivity() as MainActivity).binding.topAppBar.menu.findItem(R.id.favoriteFragment).isVisible = true
        (requireActivity() as MainActivity).binding.navBottom.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MovieAdapter(listOf(), this@FavoriteFragment)
        binding.rvData.adapter = adapter
        binding.rvData.layoutManager = LinearLayoutManager(requireContext())

        observer()
    }

    private fun observer(){
        viewModelMovie.favorite.observe(viewLifecycleOwner){
            adapter.setData(it)
            adapter.setFavorite(it)
        }

        viewModelMovie.genres.observe(viewLifecycleOwner){
            adapter.setGenre(it)
        }
    }

    fun insertFavorite(item: Movies){
        viewModelMovie.insertFavorite(item)
    }

    fun deleteFavorite(item: Movies){
        viewModelMovie.deleteFavorite(item)
    }

}