package demo.com.synerzip_ashish_savvashe.models

import java.io.Serializable


 class weatherresponse: Serializable{

   var id: Int = 0
   var base: String ? = ""
   var clouds: Clouds ? = null
   var cod: Int = 0
   var coord: Coord ? = null
   var dt: Int = 0
   var main: Main ? = null
   var name: String ? = ""
   var rain: Rain ? = null
   var sys: Sys? = null
   var timezone: Int = 0
   var visibility: Int= 0
   var weather: List<Weather>? = null
   var wind: Wind? = null
}