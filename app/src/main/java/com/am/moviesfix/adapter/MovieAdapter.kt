package com.am.moviesfix.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.am.moviesfix.R
import com.am.moviesfix.model.Movie
import com.bumptech.glide.Glide
import java.util.Locale


// Adapter class for the RecyclerView to display the list of movies - Bridge between the data source and the RecyclerView
class MovieAdapter(
    private val movieList: ArrayList<Movie>, // List of movies to be displayed in the RecyclerView (puri movie list)
    private val onItemClick: (Movie) -> Unit  // Lambda function to handle item click events
): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    // ViewHolder class to hold the views for each movie item in the RecyclerView(ek card ki sari views yaha hold hoti hai)
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val moviePoster: ImageView = itemView.findViewById(R.id.poster)
        val movieTitle: TextView = itemView.findViewById(R.id.title)
        val movieRating: TextView = itemView.findViewById(R.id.rating)
        val releaseYear : TextView = itemView.findViewById(R.id.releaseYear)

    }

    // Inflate the layout for each movie item in the RecyclerView and create a new ViewHolder(item.xml ko inflate karke ek naya card banata hai)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    // Return the total number of movies in the list (size of the list)
    override fun getItemCount(): Int {
        return movieList.size
    }

    // Bind the data for each movie item in the RecyclerView to the views in the ViewHolder
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val movie = movieList[position] // Get the current movie from the list based on its position


        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }

// Load the movie poster image using Glide library and display it in the ImageView
        Glide.with(holder.itemView.context)
            .load(movie.posterUrl)
            .into(holder.moviePoster)


        holder.movieTitle.text = movie.title
        holder.releaseYear.text = movie.releaseDate.take(4)
        holder.movieRating.text = String.format(Locale.getDefault(), "%.1f", movie.rating)

    }

    fun updateMovies(newMovies: List<Movie>) {
        movieList.clear()
        movieList.addAll(newMovies)
        notifyDataSetChanged()
    }
}