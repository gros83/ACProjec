package com.grioaldoalvarez.aplicacionbase.data

/**
 * Va a ser el acceso a la informacion de los datos relacionados
 * con las pelliculas.
 */
class MoviesRepository {
    /*
        Ahora se hace uso de MoviesClient para consultar el servicio.

     */
    suspend fun fetchPopularMovies(region: String): List<Movie> =
        MoviesClient
            .instance
            .fetchPopularMovies(region)
            .results
            /*
                Aqui tenemos que hacer un mapeo del tipo de datos RemoteMovie a Movie con mappers
                para eso vamos a usar la funcion map, para eso vamos a crear la funcion de extencion
                toDomainModel
             */
            .map { it.toDomainModel() }

    suspend fun findMovieById(id: Int): Movie =
        MoviesClient
            .instance
            .fetchMovieById(id)
            .toDomainModel()
}

private fun RemoteMovie.toDomainModel():Movie =
    Movie(
        id = id,
        title = title,
        poster = "https://image.tmdb.org/t/p/w185/$posterPath",
    )