package demo.com.synerzip_ashish_savvashe.models

import java.io.Serializable

class firebasesave: Serializable {
    var id: Int = 0
    var city_name:String = ""
    var response: weatherresponse ?= null
    var currentDate:String = ""
}