package com.am.moviesfix.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.moviesfix.MovieViewModel
import com.am.moviesfix.MovieViewModelFactory
import com.am.moviesfix.R
import com.am.moviesfix.adapter.MovieAdapter
import com.am.moviesfix.model.Movie

class HomeFragment : Fragment() {
    lateinit var progressBar : ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = MovieViewModelFactory(application = requireActivity().application)
        val viewModel = ViewModelProvider(requireActivity(), factory)[MovieViewModel::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        progressBar = view.findViewById(R.id.progressBar)


        val movieList = ArrayList<Movie>()

        val movieAdapter = MovieAdapter(
            movieList,
            onItemClick = { movie ->

                val bundle = Bundle().apply {
                    putInt("id",movie.id)
                    putString("title", movie.title)
                    putString("category", movie.category)
                    putString("posterUrl", movie.posterUrl)
                    putDouble("rating", movie.rating)
                    putString("overview", movie.overview)
                    putString("releaseDate", movie.releaseDate)
                    putBoolean("isFavourite", movie.isFavourite)
                }
                findNavController().navigate(R.id.action_home_to_detail, bundle)
            })


        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = movieAdapter


        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            movieAdapter.updateMovies(movies)

        }
        viewModel.searchResults.observe(viewLifecycleOwner) { movies ->
            movieAdapter.updateMovies(movies)
        }
        viewModel.fetchMovies()
    }

}