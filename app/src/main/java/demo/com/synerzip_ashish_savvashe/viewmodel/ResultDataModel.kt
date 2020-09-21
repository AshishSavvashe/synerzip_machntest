package demo.com.synerzip_ashish_savvashe.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import demo.com.synerzip_ashish_savvashe.models.weatherresponse
import demo.com.synerzip_ashish_savvashe.models.weatherresponse1
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
}
