package demo.com.synerzip_ashish_savvashe.models

import androidx.room.*
import java.io.Serializable
import java.util.*

@Entity(tableName = "weather_response1")

 class weatherresponse1: Serializable{
   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "id")
   var id: Int = 0

   var city_name:String = ""
   var response:String = ""
   var currentDate:String = ""



}