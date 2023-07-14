# Weather App Demo

## Summary
- **Summary:**  Android showing current location weather 
- **Created Date:** 10 June 2023
- **Last modified:** 10 June 2023 

## Feature
1. Create a browser or native-app-based application to serve as a basic weather app.
2. Search Screen
- a. Allow customers to enter a US city
- b. Call the openweathermap.org API and display the information you think a user would be interested in seeing. Be sure to has the app download and display a weather icon.
- c. Have image cache if needed
3. Auto-load the last city searched upon app launch.
4. Ask the User for location access, If the User gives permission to access the location, then retrieve weather data by default.


### Technology Used
- Kotlin
- MVVM
- Retrofit
- Data Binding
- Dialog Fragment
- RecyclerView
- Card View
- Live Data
- Constraint Layout
- TextInputField

### Backend Details

- **Webservice** :
-
- https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid={API key}
- https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}


#### Example Model


```
{
    "coord": {
        "lon": 10.99,
        "lat": 44.34
    },
    "weather": [
        {
            "id": 500,
            "main": "Rain",
            "description": "light rain",
            "icon": "10d"
        }
    ],
    "base": "stations",
    "main": {
        "temp": 296.02,
        "feels_like": 296.22,
        "temp_min": 290.9,
        "temp_max": 297.64,
        "pressure": 1011,
        "humidity": 71,
        "sea_level": 1011,
        "grnd_level": 928
    },
    "visibility": 10000,
    "wind": {
        "speed": 1.16,
        "deg": 45,
        "gust": 1.15
    },
    
    "timezone": 7200,
    "id": 3163858,
    "name": "Zocca",
    "cod": 200
}

```

```
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
) 

```

- **Library Imports**
  
```
    def lifecycle_version = "2.2.0"
    def kotlin_version = '1.3.72'
    def retrofit_version = "2.9.0"
    def logging_version = "4.3.1"

    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'

    // - - ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    // - - LiveData
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
    // cardview
    implementation 'com.google.android.material:material:1.2.1'

    // - - Retrofit2
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
    implementation "com.squareup.okhttp3:okhttp:$logging_version"
    implementation "com.squareup.okhttp3:logging-interceptor:$logging_version"

```


#### Project Structure



<img src="" width="250" height="450">


#### Screenshots


<img src="" width="250" height="450">

<img src="" width="250" height="450">

<img src="" width="250" height="450">















