package demo.com.synerzip_ashish_savvashe.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import demo.com.synerzip_ashish_savvashe.models.weatherresponse
import demo.com.synerzip_ashish_savvashe.models.weatherresponse1
import demo.com.synerzip_ashish_savvashe.models.weatherresponse4NextFiveDays
import demo.com.synerzip_ashish_savvashe.repository.ResultDataRepository


class ResultDataModel(application: Application): AndroidViewModel(application) {

     val mRepository = ResultDataRepository(application)

    fun getWeatherData(cityName: String, context: Context): MutableLiveData<weatherresponse> {

        return mRepository.getWeatherDataa(cityName,context)
    }

    fun deleteAllData() {
        ClearAllData()
    }

     fun ClearAllData() {
        mRepository.deleteAllRecord()
    }

     fun getCityWiseData(cityName: String): LiveData<weatherresponse1> {
       return mRepository.allcitywiseData(cityName)
    }

    fun getWeatherDatafromlatlong(latitude: Double, longitude: Double,context:Context) : MutableLiveData<weatherresponse> {
        return mRepository.getWeatherDataalatlong(latitude,longitude,context)
    }

    fun getWeatherData5FiveDays(cityName: String ,context:Context): MutableLiveData<weatherresponse4NextFiveDays> {
        return mRepository.getWeatherData5FiveDays(cityName,context)
    }
}
