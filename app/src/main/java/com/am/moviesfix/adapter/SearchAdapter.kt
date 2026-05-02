package com.am.moviesfix.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.am.moviesfix.R
import com.am.moviesfix.model.Movie
import com.bumptech.glide.Glide

class SearchAdapter(
    private val searchList: ArrayList<Movie>,
    private val onItemClick : (Movie) -> Unit,
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {


    class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val imgView : ImageView = itemView.findViewById<ImageView>(R.id.imgView)

        val searchTitle = itemView.findViewById<TextView>(R.id.searchTitle)
        val searchCategory = itemView.findViewById<TextView>(R.id.searchCategory)

        val searchYear = itemView.findViewById<TextView>(R.id.searchYear)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(view)

    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val search = searchList[position]


        holder.itemView.setOnClickListener {
            onItemClick(search)
        }

        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500${search.posterUrl}")
            .into(holder.imgView)

        holder.searchTitle.text = search.title
        holder.searchCategory.text = search.category
        holder.searchYear.text = search.releaseDate.take(4)

    }

    fun updateMovies(newMovie: List<Movie>) {
        searchList.clear()
        searchList.addAll(newMovie)
        notifyDataSetChanged()
    }

}

