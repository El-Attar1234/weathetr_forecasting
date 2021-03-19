package com.example.forecasting.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.forecasting.NavGraphDirections
import com.example.forecasting.R
import com.example.forecasting.ui.alarm.alarm_list.AlarmFragmentDirections
import com.example.forecasting.ui.favourite.MapsActivity
import com.example.forecasting.ui.weather.current.CurrentWeatherFragmentDirections
import com.example.forecasting.ui.weather_radar.RadarActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.io.IOException


private const val MY_PERMISSION_ACCESS_COARSE_LOCATION = 80
private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
private val REQUEST_GPS_REQUEST_CODE = 35

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by closestKodein()
    private val fusedLocationClient: FusedLocationProviderClient by instance()

    private lateinit var navController: NavHostController
    private lateinit var navHostFragment: NavHostFragment

    //private lateinit var intent: Intent
    private lateinit var prefEditor: SharedPreferences.Editor


    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUi()
        fab.setOnClickListener {
            initializeDialog()
        }

    }


    private fun initializeDialog() {
        val inflater = LayoutInflater.from(this)
        val inflate_view: View = inflater.inflate(R.layout.statistics_graph, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(inflate_view)
        builder.setCancelable(true)
        builder.setTitle("     Weather Statistics")
        builder.setIcon(R.drawable.ic_mostly_cloudy)
        builder.create()
        builder.create().show()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.radarFragment -> {
              //  val action = CurrentWeatherFragmentDirections.radarActivityAction()
               // navController.navigate(action)
                val intent=Intent(this, RadarActivity::class.java)
                 startActivity(intent)
                true
            }
            R.id.addAlarm -> {
                val detailsAction =NavGraphDirections.addNewAlarm(-1)
                navController.navigate(detailsAction)
                true
            }
            R.id.addFavourites->{
                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("case", "favourite")
                startActivity(intent)
                true
            }
            else -> {

                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun initializeUi() {
        bottom_nav.background = null
        bottom_nav.menu.getItem(2).isEnabled = false
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController() as NavHostController
        bottom_nav.setupWithNavController(navController)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController)
    }


}