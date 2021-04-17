package cl.rentalea.rentalapp.di

import cl.rentalea.rentalapp.network.ApiServices
import cl.rentalea.rentalapp.network.RequestInterceptor
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        OkHttpClient.Builder()
                .addInterceptor(RequestInterceptor())
                .build()
    }

    single {
        val url = ""

        Retrofit.Builder()
                .client(get<OkHttpClient>())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
                .build()
    }

    single { get<Retrofit>().create(ApiServices::class.java) }
}