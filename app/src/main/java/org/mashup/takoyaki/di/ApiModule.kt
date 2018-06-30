package org.mashup.takoyaki.di


import com.google.gson.GsonBuilder

import org.mashup.takoyaki.BuildConfig
import org.mashup.takoyaki.data.remote.api.ApiService
import org.mashup.takoyaki.data.remote.util.MainThreadCallAdapter
import org.mashup.takoyaki.data.remote.util.NetworkErrorInterceptor

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        builder.addInterceptor(NetworkErrorInterceptor())

        return builder.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(client: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
        builder.client(client)
        builder.baseUrl(BuildConfig.BASE_URL)
        builder.addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().setPrettyPrinting()
                        .create()))
        builder.addCallAdapterFactory(MainThreadCallAdapter.create(AndroidSchedulers.mainThread()))
        builder.addCallAdapterFactory(
                RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))

        return builder.build()
    }

    @Provides
    @Singleton
    internal fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
