package com.arief.moviedb.ui.movie.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arief.moviedb.R
import com.arief.moviedb.adapter.MovieAdapter
import com.arief.moviedb.databinding.FragmentPopularBinding
import com.arief.moviedb.model.Movies
import com.arief.moviedb.ui.movie.MovieViewModel
import com.arief.moviedb.utils.Status
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding
    private val viewModel : PopularViewModel by viewModel()
    private val viewModelMovie : MovieViewModel by activityViewModel()
    private lateinit var adapter: MovieAdapter
    private var loadingMore = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_popular,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter(listOf(), this@PopularFragment)
        binding.rvData.adapter = adapter
        binding.rvData.layoutManager = LinearLayoutManager(requireContext())
        if(viewModel.listLoadedMovies.isNotEmpty()) {
            adapter.addData(viewModel.listLoadedMovies)
            binding.rvData.scrollToPosition(viewModel.lastPositionAdapter)
            binding.mainShimmer.apply {
                stopShimmer()
                visibility = View.GONE
            }
            binding.rvData.visibility = View.VISIBLE
        }
        binding.rvData.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = binding.rvData.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1){
                    if (!loadingMore) {
                        binding.progressBar.visibility = View.VISIBLE
                        viewModel.getMovies()
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                viewModel.setLastPosition(layoutManager.findFirstVisibleItemPosition())
            }
        })

        observer()
    }

    private fun observer(){
        viewModelMovie.favorite.observe(viewLifecycleOwner){
            adapter.setFavorite(it)
        }

        viewModelMovie.genres.observe(viewLifecycleOwner){
            adapter.setGenre(it)
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    if(adapter.itemCount == 0) {
                        binding.mainShimmer.apply {
                                stopShimmer()
                                visibility = View.GONE
                                binding.rvData.visibility = View.VISIBLE
                                adapter.addData(it.data!!)
                        }
                    }else{
                        binding.progressBar.visibility = View.GONE
                        adapter.addData(it.data!!)
                        binding.rvData.scrollToPosition(viewModel.lastPositionAdapter)
                    }
                }
                Status.LOADING -> {
                    if(adapter.itemCount == 0) {
                        binding.mainShimmer.apply {
                            startShimmer()
                            visibility = View.VISIBLE
                        }
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

    fun insertFavorite(item: Movies){
        viewModelMovie.insertFavorite(item)
    }

    fun deleteFavorite(item: Movies){
        viewModelMovie.deleteFavorite(item)
    }
}