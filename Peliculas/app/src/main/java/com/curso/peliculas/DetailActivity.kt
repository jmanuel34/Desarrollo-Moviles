package com.curso.peliculas

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.curso.peliculas.model.Movie
import com.curso.peliculas.network.OmdbApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var moviePoster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieRating: TextView
    private lateinit var movieDirector: TextView
    private lateinit var moviePlot: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Inicializar vistas
        moviePoster = findViewById(R.id.moviePoster)
        movieTitle = findViewById(R.id.movieTitle)
        movieRating = findViewById(R.id.movieRating)
        movieDirector = findViewById(R.id.movieDirector)
        moviePlot = findViewById(R.id.moviePlot)

        val movieID = intent.getStringExtra("MOVIE_ID") ?: ""

        if (movieID.isNotEmpty()) {
            fetchMovieDetails(movieID)
        } else {
            Toast.makeText(this, "Error: No se encontró el ID de la película", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun fetchMovieDetails(imdbID: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(OmdbApiService::class.java)
        
        service.getMovieDetails(imdbID, BuildConfig.OMDB_API_KEY).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    val movie = response.body()
                    movie?.let { showMovieDetails(it) }
                } else {
                    Toast.makeText(this@DetailActivity, "Error al obtener detalles", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Toast.makeText(this@DetailActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showMovieDetails(movie: Movie) {
        movieTitle.text = movie.title
        movieRating.text = "⭐ ${movie.rating ?: "N/A"}"
        movieDirector.text = "Director: ${movie.director ?: "Unknown"}"
        moviePlot.text = movie.plot ?: "No plot available."

        Glide.with(this)
            .load(movie.poster)
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .error(android.R.drawable.ic_menu_report_image)
            .into(moviePoster)
    }
}
