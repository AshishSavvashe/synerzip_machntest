package demo.com.synerzip_ashish_savvashe.models

import androidx.room.*
import com.google.firebase.database.IgnoreExtraProperties
import dagger.Module
import dagger.Provides
import java.io.Serializable
import javax.inject.Singleton


@Module
@Entity(tableName = "weather_response1")

@IgnoreExtraProperties
 class weatherresponse1: Serializable{
  @Singleton

  @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "id")
   var id: Int = 0

   var city_name:String = ""
   var response:String = ""
   var currentDate:String = ""



}