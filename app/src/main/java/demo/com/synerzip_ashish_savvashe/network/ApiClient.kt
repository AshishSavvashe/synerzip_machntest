package demo.com.synerzip_ashish_savvashe.network


import com.google.gson.GsonBuilder
import demo.com.synerzip_ashish_savvashe.constant.BASE_URL
import demo.com.synerzip_ashish_savvashe.constant.NETWORK_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

object ApiClient {

    private var retrofit: Retrofit? = null

    val client: Retrofit
        get() {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY


            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()

            val builder = GsonBuilder()
                .setLenient()
                .create()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(builder)).build()

            return retrofit!!

        }
}
