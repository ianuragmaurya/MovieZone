package com.am.moviesfix.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.am.moviesfix.MovieViewModel
import com.am.moviesfix.R
import com.am.moviesfix.model.Movie
import com.bumptech.glide.Glide
import java.time.Year

class DetailFragment : Fragment() {

    lateinit var detailTitle: TextView
    lateinit var detailCategory: TextView
    lateinit var detailPoster: ImageView
    lateinit var detailRating: TextView
    lateinit var detailOverview: TextView
    lateinit var favourite: ImageView
    lateinit var releaseYear: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailTitle = view.findViewById(R.id.detailTitle)
        detailCategory = view.findViewById(R.id.detailCategory)
        detailPoster = view.findViewById(R.id.detailPoster)
        detailRating = view.findViewById(R.id.detailRating)
        detailOverview = view.findViewById(R.id.detailOverview)
        releaseYear = view.findViewById(R.id.releaseYear)
        favourite = view.findViewById(R.id.favourite)

        val viewModel = ViewModelProvider(requireActivity())[MovieViewModel::class.java]



        val bundle = arguments
        val title = bundle?.getString("title")
        val category = bundle?.getString("category")
        val posterUrl = bundle?.getString("posterUrl")
        val rating = bundle?.getDouble("rating")
        val overview = bundle?.getString("overview")
        val releaseDate = bundle?.getString("releaseDate")
        val isFavourite = bundle?.getBoolean("isFavourite") ?: false


        Glide.with(requireContext())
            .load(posterUrl)
            .into(detailPoster)

        detailTitle.text = title
        detailCategory.text = category
        detailRating.text = String.format("%.1f", rating)
        detailOverview.text = overview
        releaseYear.text = releaseDate?.take(4)


        val movieId = bundle?.getInt("id") ?: 0
        viewModel.getMovieById(movieId).observe(viewLifecycleOwner) {movie ->
            movie?.let {

                favourite.setImageResource(
                    if(it.isFavourite) R.drawable.ic_favorite_field else R.drawable.ic_favorite
                )
                favourite.setOnClickListener {
                    viewModel.toggleFavourite(movie)
                }
            }

        }


        val movie = Movie(
            id = movieId ?: 0,
            title = title ?: "",
            posterUrl = posterUrl ?: "",
            category = category ?: "",
            rating = rating ?: 0.0,
            overview = overview ?: "",
            releaseDate = releaseDate ?: "",
            isFavourite = isFavourite
        )
        favourite.setOnClickListener {
            viewModel.toggleFavourite(movie)

        }
    }

}