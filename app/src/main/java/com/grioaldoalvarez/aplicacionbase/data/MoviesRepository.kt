package com.grioaldoalvarez.aplicacionbase.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

/**
 * Va a ser el acceso a la informacion de los datos relacionados
 * con las pelliculas.
 */
class MoviesRepository {
    /*
    Como delay es una funcion suspend necesitamos gestionar la corutina poniedo un launch o algo por el
    estilo o si se quiere delegar esa gestion a una coapa superior. Lo que se debe hacer es declarar nuestra
    funcion como suspend también. Eso quiere decir que esta funcion puede llamar otras funciones suspend adentro
    porque al llamarla en algun momento va a tener que estar dentro de una corutina.
    Si se quiere que esto no se ejecute en el hilo principal para que no se bloque el hilo principal
    se invoca la función withContext y le decimos en que contexto queremos que se ejecute.
    En este caso Dispatchers.IO
    */
    suspend fun fetchPopularMovies(): List<Movie> = withContext(Dispatchers.IO){
        delay(2000)
        // aqui ya no se usa un return ya que como es una lamba, el valor de retorno es la ultima
        //linea(ultima expresion) de la lambda
        movies
    }
}