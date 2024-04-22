package com.grioaldoalvarez.aplicacionbase.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Aqui vamos a definir cuales son las peticiones que hacemos al servidor
interface MoviesService {
    /*
    Una cosa importante que tiene retrofit es que tiene soporte para corutinas asi es que si le agregamos
    la palabra suspend a la funcion va a hacer que la función se ejecute en un hilo secundario y no nos bloquee
    el hilo principal.
     */
    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun fetchPopularMovies(@Query("region") region:String): RemoteResult

    /*
        En este caso el identificador no se le pasa como QueryParam si no que se le pasa como una parte
        de la url, para poder hacer esto se lo pone entre llaves en el punto de la url donde iria
        sustituido el valor
     */
    @GET("movie/{id}")
    suspend fun fetchMovieById(@Path("id") id: Int) : RemoteMovie
}