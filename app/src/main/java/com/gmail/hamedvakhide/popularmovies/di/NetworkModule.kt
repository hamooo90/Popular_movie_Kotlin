package com.gmail.hamedvakhide.popularmovies.di

import com.gmail.hamedvakhide.popularmovies.BuildConfig
import com.gmail.hamedvakhide.popularmovies.network.MovieService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideCallFactory(
        interceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: Interceptor
    ): Call.Factory{
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()


    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Provides
    @Singleton
    fun provideRxJavaCallAdapterFactory(): RxJava3CallAdapterFactory = RxJava3CallAdapterFactory.create()

    @Provides
    @Singleton
    @Named("tmdb_base_url")
    fun provideBaseUrl(): String = "https://api.themoviedb.org/3/"


    @Provides
    @Singleton
    @Named("tmdb_api_key")
    fun provideApiKey(): String = BuildConfig.TMDB_API_KEY


    @Provides
    @Singleton
    fun provideApiKeyInterceptor(@Named("tmdb_api_key") apiKey: String): Interceptor{
        return Interceptor.invoke {
            val originalRequest = it.request()
            val originalUrl = originalRequest.url
            val newUrl = originalUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()

            it.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        callFactory: Call.Factory,
        moshiConverterFactory: MoshiConverterFactory,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory,
        @Named("tmdb_base_url") baseUrl: String
    ): Retrofit{
        return Retrofit.Builder()
            .callFactory(callFactory)
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .addConverterFactory(moshiConverterFactory)
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)

}







