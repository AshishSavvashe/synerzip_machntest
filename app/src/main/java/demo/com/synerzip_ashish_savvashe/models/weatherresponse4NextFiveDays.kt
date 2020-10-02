package demo.com.synerzip_ashish_savvashe.models

data class weatherresponse4NextFiveDays(
    var city: City,
    var cnt: Int,
    var cod: String,
    var list: List<AllData>,
    var message: Int
) {
    data class City(
        var coord: Coord,
        var country: String,
        var id: Int,
        var name: String,
        var population: Int,
        var sunrise: Int,
        var sunset: Int,
        var timezone: Int
    ) {
        data class Coord(
            var lat: Double,
            var lon: Double
        )
    }

    data class AllData (
        var clouds: Clouds,
        var dt: Int,
        var dt_txt: String,
        var main: Main,
        var pop: Double,
        var rain: Rain,
        var sys: Sys,
        var visibility: Int,
        var weather: List<Weather>,
        var wind: Wind
    ) {
        data class Clouds(
            var all: Int
        )

        data class Main(
            var feels_like: Double,
            var grnd_level: Int,
            var humidity: Int,
            var pressure: Int,
            var sea_level: Int,
            var temp: Double,
            var temp_kf: Double,
            var temp_max: Double,
            var temp_min: Double
        )

        data class Rain(
            var `3h`: Double
        )

        data class Sys(
            var pod: String
        )

        data class Weather(
            var description: String,
            var icon: String,
            var id: Int,
            var main: String
        )

        data class Wind(
            var deg: Int,
            var speed: Double
        )
    }
}