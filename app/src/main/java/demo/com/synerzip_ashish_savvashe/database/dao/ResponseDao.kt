package demo.com.synerzip_ashish_savvashe.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import demo.com.synerzip_ashish_savvashe.models.weatherresponse1


@Dao
interface ResponseDao {

    @get:Query("SELECT * from weather_response1")
    val allData: LiveData<weatherresponse1>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(word: weatherresponse1)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<weatherresponse1>)

    @Query("DELETE FROM weather_response1")
     fun deleteAll()


    @Query("SELECT * from weather_response1 where city_name =:cityName" )
     fun getAllCityWiseData(cityName: String):LiveData<weatherresponse1>
}
