package demo.com.synerzip_ashish_savvashe.constant


const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
const val NETWORK_TIMEOUT:Long = 60
const val UNITS = "metric"

val ACTION_SEND_MESSAGE = "action_send_message"


 val WEATHER_STATUS = arrayOf(
    "Thunderstorm",
    "Drizzle",
    "Rain",
    "Snow",
    "Atmosphere",
    "Clear",
    "Few Clouds",
    "Broken Clouds",
    "Cloud"
)

enum class Status {
   SUCCESS,
   ERROR,
   LOADING
}