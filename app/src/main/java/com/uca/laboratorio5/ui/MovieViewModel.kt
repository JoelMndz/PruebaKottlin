package com.uca.laboratorio5.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.uca.laboratorio5.MovieReviewerApplication
import com.uca.laboratorio5.data.model.MovieModel
import com.uca.laboratorio5.repositories.MovieRepository
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY

class MovieViewModel (private val repository: MovieRepository):ViewModel(){
    val name = MutableLiveData("");
    val description = MutableLiveData("");
    val status = MutableLiveData("");
    fun getMovies() = repository.getMovies()

    private fun addMovies(movie:MovieModel) = repository.addMovies(movie)

    fun createMovie(){
        if(!validateData()){
            status.value = WRONG_INFORMATION;
            return
        }

        addMovies(
            MovieModel(
                name.value!!,
                description.value!!
            )
        )
        clearData()

        status.value = MOVIE_CREATED
    }

    private fun validateData():Boolean{
        when{
            name.value.isNullOrBlank() -> return false
            description.value.isNullOrBlank() -> return false
        }
        return true
    }

    private fun clearData(){
        name.value = ""
        description.value = ""
    }

    fun clearStatus(){
        status.value = INACTIVE
    }

    fun setSelectedMovie(movie: MovieModel){
        name.value = movie.name;
        description.value = movie.directory
    }

    companion object{
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as MovieReviewerApplication
                MovieViewModel(app.movieRepository)
            }
        }

        const val MOVIE_CREATED = "Movie created"
        const val WRONG_INFORMATION = "Wrong information"
        const val INACTIVE = ""
    }

}