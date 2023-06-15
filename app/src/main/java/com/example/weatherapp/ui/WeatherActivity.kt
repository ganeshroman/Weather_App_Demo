package com.example.weatherapp.ui


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.ViewModel.WeatherViewModel
import com.example.weatherapp.databinding.ActivityMainBinding


class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var context: Context
    lateinit var locationManager: LocationManager
    var hasGps: Boolean?=false
    var hasNetwork: Boolean?=false
     var locationGps:Location?=null
     var locationNetwork:Location?=null
     var preferredLocation:Location?=null


    companion object {
        val requestcode:Int=1234
        val minTimeinMS:Long=5000
        val minDistanceinM:Float=0F
    }

    lateinit var mainActivityViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this@WeatherActivity

        mainActivityViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        binding.btnClick.setOnClickListener {
            getUpdateWeather()
        }

        binding.btnCurrentClick.setOnClickListener{

            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    ,requestcode)
            }
            else{
                getLocation()
            }

        }

    }


    fun getUpdateWeather(){
        binding.wp7progressBar.visibility = View.VISIBLE

        /*var lat:String ="10.99"
        var lon:String = "44.34"*/


        var lat: String = binding.edtLatitude.text.toString() ?: "10.99"
        var lon: String = binding.edtLongitude.text.toString() ?: "44.34"

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



                })

        } else {
            Toast.makeText(context, "Enter latitude, Longitude.", Toast.LENGTH_SHORT).show()
        }

        binding.wp7progressBar.visibility = View.GONE
    }



    @SuppressLint("MissingPermission")
    private fun getLocation() {
        //val uid = Firebase.auth.currentUser?.uid
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps as Boolean || hasNetwork as Boolean) {

            if (hasGps as Boolean) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTimeinMS, minDistanceinM, object :
                    LocationListener {
                    override fun onLocationChanged(p0: Location) {
                        if (p0 != null) {
                            locationGps = p0

                            //locationGps!!.longitude,
                            //locationGps!!.latitude
                            Log.d("Location GPS",locationGps.toString())

                        }
                    }

                })

                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null)
                    locationGps = localGpsLocation
            }
            if (hasNetwork as Boolean) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTimeinMS, minDistanceinM, object : LocationListener {
                    override fun onLocationChanged(p0: Location) {
                        if (p0 != null) {
                            locationNetwork = p0

                            Log.d("Location Network",locationNetwork.toString())
                            //

                        }
                    }

                })

                val localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (localNetworkLocation != null)
                    locationNetwork = localNetworkLocation
            }

            if(locationGps!= null && locationNetwork!= null){
                if(locationGps!!.accuracy > locationNetwork!!.accuracy){
                    // received location gps preffered
                    preferredLocation=locationGps

                }else{

                    // received location network preffered
                    preferredLocation=locationNetwork
                }


            }else{
                if(locationGps!= null ){
                    preferredLocation=locationGps
                }

                if(locationNetwork!=null){
                    preferredLocation=locationNetwork
                }
            }

            binding.edtLatitude.setText(preferredLocation?.latitude.toString())
            binding.edtLongitude.setText(preferredLocation?.longitude.toString())

            getUpdateWeather()

        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }


    override fun onActivityResult(requesCode: Int, resultCode: Int, data: Intent?) {
        Log.d("Permission Result", "onActivityResult: "+requesCode+", "+resultCode)

        if (requesCode == requestcode && resultCode==PackageManager.PERMISSION_GRANTED) {

            getLocation()
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            }*/
        }
        super.onActivityResult(requesCode, resultCode, data)
    }

}