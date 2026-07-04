package com.am.moviesfix.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.moviesfix.MovieViewModel
import com.am.moviesfix.MovieViewModelFactory
import com.am.moviesfix.R
import com.am.moviesfix.adapter.MovieAdapter
import com.am.moviesfix.model.Movie

class FavouriteFragment : Fragment() {

     override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View?
     {
         // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_favourite, container, false)
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvFavouriteMovies)

        val factory = MovieViewModelFactory(application = requireActivity().application)
        val viewModel = ViewModelProvider(requireActivity(), factory)[MovieViewModel::class.java]

        val emptyState = view.findViewById<LinearLayout>(R.id.emptyState)


            val movieAdapter = MovieAdapter(ArrayList(),
                onItemClick = {movie ->
                val bundle = Bundle().apply {
                    putInt("id", movie.id)
                    putString("title", movie.title)
                    putString("category", movie.category)
                    putString("posterUrl", movie.posterUrl)
                    putDouble("rating", movie.rating)
                    putString("overview", movie.overview)
                    putBoolean("isFavourite", movie.isFavourite)
                }
                findNavController().navigate(R.id.action_favourite_to_detail, bundle)

            })


            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerView.adapter = movieAdapter

        viewModel.getFavouriteMovies().observe(viewLifecycleOwner) {movies ->
            movieAdapter.updateMovies(movies)

            if (movies.isEmpty()) {
                emptyState.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }else {
                emptyState.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }

        }
    }
}