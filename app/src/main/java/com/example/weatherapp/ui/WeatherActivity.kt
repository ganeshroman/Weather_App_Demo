package com.example.weatherapp.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.ViewModel.WeatherViewModel
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var context: Context

    lateinit var mainActivityViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this@WeatherActivity

        mainActivityViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        binding.btnClick.setOnClickListener {

            binding.wp7progressBar.visibility = View.VISIBLE

            /*var lat:String ="10.99"
            var lon:String = "44.34"*/


            var lat: String = binding.edtLatitude.text.toString() ?: "10.99"
            var lon: String = binding.edtLatitude.text.toString() ?: "44.34"

            if (lat!=null && lat.length > 1 && lon!=null && lon.length > 1) {

                mainActivityViewModel.getWeather(lat, lon)!!
                    .observe(this, Observer { serviceSetterGetter ->

                        binding.wp7progressBar.visibility = View.INVISIBLE

                        val msg = serviceSetterGetter.toString()


                        binding.txtLatitude.text =
                            context.getString(R.string.strLatitude) + serviceSetterGetter.coord.lat.toString();
                        binding.txtLongitude.text =
                            context.getString(R.string.strLongitude) + serviceSetterGetter.coord.lon.toString();
                        binding.txtMinTemprature.text =
                            context.getString(R.string.strMinimum_temprature) + serviceSetterGetter.main.tempMin.toString()
                        binding.txtMaxTemprature.text =
                            context.getString(R.string.strMax_temprature) + serviceSetterGetter.main.tempMax.toString()
                        binding.txtHumidity.text =
                            context.getString(R.string.strHumidity) + serviceSetterGetter.main.humidity.toString();
                        binding.txtVisibility.text =
                            context.getString(R.string.strVisibility) + serviceSetterGetter.visibility.toString();
                        binding.txtWindSpeed.text =
                            context.getString(R.string.strWind_speed) + serviceSetterGetter.wind.speed.toString();
                        binding.txtWeather.text =
                            context.getString(R.string.strWeather) + serviceSetterGetter.weather.get(
                                0
                            ).description

                        //binding.lblResponse.text = msg

                    })

            } else {
                Toast.makeText(context, "Enter latitude, Longitude.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}