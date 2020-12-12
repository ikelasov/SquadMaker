package com.example.squadmaker.di

import androidx.room.Room
import com.example.squadmaker.model.localdatasouce.roomdatabase.SquadDatabase
import com.example.squadmaker.model.remotedatasource.retrofit.api.MarvelApiService
import com.example.squadmaker.repository.RepositoryImpl
import com.example.squadmaker.viewmodel.DetailedViewModelImpl
import com.example.squadmaker.viewmodel.MainViewModelImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// region Fields

private const val MARVEL_API_BASE_URL: String = "https://gateway.marvel.com:443/"

// endregion

// region Modules

val networkModule = module {
    single { provideRetrofit() }
}

val roomDatabaseModule = module {
    single {
        Room.databaseBuilder(get(), SquadDatabase::class.java, "marvel_db").build()
    }
}

val repositoryModule = module {
    single {
        RepositoryImpl(
            squadDatabase = get(),
            marvelApiService = get()
        )
    }
}

val viewModelsModule = module {
    viewModel { MainViewModelImpl(repository = get()) }
    viewModel { DetailedViewModelImpl(repository = get()) }
}

// endregion

// region Private Functions

private fun provideRetrofit(): MarvelApiService {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(MARVEL_API_BASE_URL)
        .client(getHttpClientInterceptor())
        .build()
        .create(MarvelApiService::class.java)
}

private fun getHttpClientInterceptor(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    val httpClient = OkHttpClient.Builder()

    return httpClient.addInterceptor(logging).build()
}

// endregion