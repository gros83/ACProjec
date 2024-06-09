package com.grioaldoalvarez.aplicacionbase.data

import com.grioaldoalvarez.aplicacionbase.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

// Es el que nos permite hacer las peticiones al servidor
object MoviesClient {
    // Aqui vamos a agregar un interceptor para agregarle el ApiKey a las peticiones que lo necesiten
    // El interceptor captura las peticiones y el decide si le quiere quitar o añadir cosas a la petición
    val okHttpClient = OkHttpClient.Builder()
        //Aqui se pasa una funcion que lo que va a hacer es convertir el apiKey para poder usarlo como un QueryParam
        // Escribirlo .addInterceptor(::apikeyAsQuery) es lo mismo que escribirlo como .addInterceptor{ apikeyAsQuery(it) }
        .addInterceptor { apikeyAsQuery(it) }
        .build()

    // Nos creamos este objeto para que en caso de que solo queramos algunas propiedades del json de respuesta
    // y no nos pida definir todas la propiedades del json de respuesta, entonces, creamos nuestro objeto json
    // con el builder y le decimos ignoreUnknownKeys para ignorar las claves desconocidas.
    private val json = Json {
        ignoreUnknownKeys = true
    }

    val instance = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(okHttpClient)
        // Utilizamos el objeto Json de KotlinxSerialization, utilizamos la funcion de extension que nos da
        // el converter que hemos importado que es asConverterFactory y le tenemos que decir el tipo de datos
        // que va a utilizar que es "application/json" y hay que convertirlo a MediaType
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType() ))
        .build()
        .create<MoviesService>()
}

private fun apikeyAsQuery(chain: Interceptor.Chain) = chain.proceed(
    chain
        .request()
        //Aqui le decimos que cree una request
        .newBuilder()
        // A esta nueva request le vamos a dar una nueva url
        .url(
            // Esa url se va a generar a partir de la url original
            chain.request().url
                .newBuilder()
                // A la que le vamos a añadir un QueryParam con el Api Key
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build()
        ).build()
)

