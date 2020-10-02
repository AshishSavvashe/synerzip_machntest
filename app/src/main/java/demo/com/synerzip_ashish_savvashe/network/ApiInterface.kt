package demo.com.synerzip_ashish_savvashe.network

import com.google.gson.JsonObject
import dagger.Component
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather?")
    fun getCurrentWeather(
        @Query("q") q: String?,
        @Query("appid") appId: String?
    ): Call<JsonObject>

    @GET("forecast?")
    fun getWeatherData5days(
        @Query("q") q: String?,
        @Query("appid") appId: String?
    ): Call<JsonObject>

    @GET("weather?")
    fun getCurrentWeatheronlatlog(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("appid") appId: String?
    ): Call<JsonObject>
}
