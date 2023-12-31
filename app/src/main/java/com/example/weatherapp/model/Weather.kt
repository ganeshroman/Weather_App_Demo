package com.example.weatherapp.model


data class Weather (
    val coord: Coord,
    val weather: List<WeatherElement>,
    val base: String,
    val main: Main,
    val visibility: Long,
    val wind: Wind,
    val rain: Rain,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
) {
    //public fun toJson() = KlaxonJson()//.toJsonString(this)


    /*companion object {
        public fun fromJson(json: String) = klaxon.parse<Weather>(json)
    }*/
}

data class Clouds (
    val all: Long
)

data class Coord (
    val lon: Double,
    val lat: Double
)

data class Main (
    val temp: Double,

    //@Json(name = "feels_like")
    val feelsLike: Double,

    //@Json(name = "temp_min")
    val tempMin: Double,

    //@Json(name = "temp_max")
    val tempMax: Double,

    val pressure: Long,
    val humidity: Long,

    //@Json(name = "sea_level")
    val seaLevel: Long,

    //@Json(name = "grnd_level")
    val grndLevel: Long
)

data class Rain (
    //@Json(name = "1h")
    val the1H: Double
)

data class Sys (
    val type: Long,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class WeatherElement (
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind (
    val speed: Double,
    val deg: Long,
    val gust: Double
)