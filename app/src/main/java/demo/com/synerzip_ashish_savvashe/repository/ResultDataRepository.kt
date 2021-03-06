package demo.com.synerzip_ashish_savvashe.repository

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import demo.com.synerzip_ashish_savvashe.database.AppDatabase
import demo.com.synerzip_ashish_savvashe.database.dao.ResponseDao
import demo.com.synerzip_ashish_savvashe.models.weatherresponse
import demo.com.synerzip_ashish_savvashe.models.weatherresponse1
import demo.com.synerzip_ashish_savvashe.models.weatherresponse4NextFiveDays
import demo.com.synerzip_ashish_savvashe.network.ApiClient
import demo.com.synerzip_ashish_savvashe.network.ApiInterface
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultDataRepository(application: Application): CoroutineScope by MainScope(){

     var responseModelData: MutableLiveData<weatherresponse> = MutableLiveData()
     var responseModelData1: MutableLiveData<weatherresponse4NextFiveDays> = MutableLiveData()
     private val resultDao: ResponseDao

     val retrofit = ApiClient.client
     val requestInterface = retrofit.create(ApiInterface::class.java)

    init {
        val database = AppDatabase.getDatabase(application)
        resultDao = database?.responseDao()!!
        responseModelData = MutableLiveData()
    }

    //18.642217, 73.750264
    //http://api.openweathermap.org/data/2.5/weather?lat=18.642217&lon=73.750264&appid=f2508fbce2edb3c268faf18d96e39a48
    @Throws(Exception::class)
     fun getWeatherDataa(cityName: String, context: Context): MutableLiveData<weatherresponse>{

        val accessTokenCall: Call<JsonObject> = requestInterface.getCurrentWeather(q = cityName,appId =  "f2508fbce2edb3c268faf18d96e39a48")
         launch(Dispatchers.IO) {
             async { callApiWithAccessToken(accessTokenCall, context,cityName,"","") }
         }
        return responseModelData
    }

    fun getWeatherData5FiveDays(cityName: String, context: Context): MutableLiveData<weatherresponse4NextFiveDays> {

        val accessTokenCall: Call<JsonObject> = requestInterface.getWeatherData5days(q = cityName,appId =  "f2508fbce2edb3c268faf18d96e39a48")
        launch(Dispatchers.IO) {
            async { callApiWithAccessToken2(accessTokenCall, context,cityName) }
        }
        return responseModelData1
    }


    fun getWeatherDataalatlong(latitude: Double, longitude: Double, context: Context): MutableLiveData<weatherresponse> {
        val accessTokenCall: Call<JsonObject> = requestInterface.getCurrentWeatheronlatlog(latitude.toString(),longitude.toString(),appId =  "f2508fbce2edb3c268faf18d96e39a48")
        launch(Dispatchers.IO) {
            async { callApiWithAccessToken(accessTokenCall, context,"",latitude.toString(),longitude.toString()) }
        }
        return responseModelData
    }

     fun callApiWithAccessToken(accessTokenCall: Call<JsonObject>, context: Context, cityName: String, lat: String, long: String){

        try {
            accessTokenCall.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                    if (response.body()!=null) {
                        val response1 = Gson().fromJson(response.body() , weatherresponse::class.java)

                        responseModelData.value = response1

                        var inserObject = weatherresponse1()
                        inserObject.id = 0
                        inserObject.city_name = cityName
                        inserObject.response = response1.toString()
                        inserObject.currentDate = System.currentTimeMillis().toString()
                        InsertAllAsyncTask(resultDao).execute(inserObject)

                    } else {
                       // Toast. makeText(context, " No Record Data Found", Toast. LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    try {
                        Log.e(ContentValues.TAG, "callApiWithAccessToken:")
                    } catch (e: Exception) {
                        Log.e(ContentValues.TAG, "onFailure: $e")
                    }
                }
            })
        } catch (e: Exception) {
            try {
                Log.e(ContentValues.TAG, "callApiWithAccessToken: $e")
            } catch (e1: Exception) {
                Log.e(ContentValues.TAG, "callApiWithAccessToken: $e1")
            }
        }
    }

    private fun callApiWithAccessToken2(accessTokenCall: Call<JsonObject>, context: Context, cityName: String) {

        try {
            accessTokenCall.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                    if (response.body()!=null) {
                        val response1 = Gson().fromJson(response.body() , weatherresponse4NextFiveDays::class.java)

                        responseModelData1.value = response1

                    } else {
                        Toast. makeText(context, " No Record Data Found", Toast. LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    try {
                        Log.e(ContentValues.TAG, "callApiWithAccessToken:")
                    } catch (e: Exception) {
                        Log.e(ContentValues.TAG, "onFailure: $e")
                    }
                }
            })
        } catch (e: Exception) {
            try {
                Log.e(ContentValues.TAG, "callApiWithAccessToken: $e")
            } catch (e1: Exception) {
                Log.e(ContentValues.TAG, "callApiWithAccessToken: $e1")
            }
        }
    }

    fun deleteAllRecord() {
        DeleteAllrecordAsyncTask(resultDao).execute()
    }

     fun allcitywiseData(cityName: String): LiveData<weatherresponse1> {
        return resultDao.getAllCityWiseData(cityName)
    }

    class InsertAllAsyncTask internal constructor(private val mAsyncTaskDao: ResponseDao) :
       AsyncTask<weatherresponse1, Void, Void>() {


        override fun doInBackground(vararg params: weatherresponse1): Void? {
           mAsyncTaskDao.insert(params[0])
           return null
       }
    }

    private class DeleteAllrecordAsyncTask internal constructor(private val mAsyncTaskDao: ResponseDao) :
        AsyncTask<Int, Void, Void>() {
        override fun doInBackground(vararg params: Int?): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }
    
}


