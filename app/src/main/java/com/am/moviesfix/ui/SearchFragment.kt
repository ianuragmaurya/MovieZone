package com.am.moviesfix.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.moviesfix.MovieViewModel
import com.am.moviesfix.MovieViewModelFactory
import com.am.moviesfix.R
import com.am.moviesfix.adapter.SearchAdapter
import com.am.moviesfix.model.Movie

class SearchFragment : Fragment() {

    private lateinit var searchRv: RecyclerView
    private lateinit var searchBox: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBox = view.findViewById(R.id.searchBox)
        searchRv = view.findViewById(R.id.searchRv)

        val factory = MovieViewModelFactory(application = requireActivity().application)
        val viewModel = ViewModelProvider(requireActivity(), factory)[MovieViewModel::class.java]

        val movieList = ArrayList<Movie>()

        val searchAdapter = SearchAdapter(movieList) { movie ->
            val bundle = Bundle().apply {
                putInt("id", movie.id)
                putString("title", movie.title)
                putString("category", movie.category)
                putString("posterUrl", movie.posterUrl)
                putDouble("rating", movie.rating)
                putString("overview", movie.overview)
                putBoolean("isFavourite", movie.isFavourite)
                putString("releaseDate", movie.releaseDate)
            }
            findNavController().navigate(R.id.action_search_to_detail, bundle)
        }


        searchRv.layoutManager = LinearLayoutManager(requireContext())
        searchRv.adapter = searchAdapter

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            searchAdapter.updateMovies(movies)
        }

        searchBox.addTextChangedListener { text ->
            if (text.isNullOrEmpty()) {
                searchAdapter.updateMovies(viewModel.movies.value ?: emptyList())
            } else {
                viewModel.searchMovies(text.toString())
            }

            viewModel.searchResults.observe(viewLifecycleOwner) {movies ->
                searchAdapter.updateMovies(movies)
            }
        }
    }
}
