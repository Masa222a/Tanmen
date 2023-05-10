package jp.re.tanmen.view.Activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.AppLaunchChecker
import androidx.core.content.ContextCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import jp.re.tanmen.model.API.ShopService
import jp.re.tanmen.R
import jp.re.tanmen.databinding.ActivityMainBinding
import jp.re.tanmen.view.Fragment.MainFragment
import timber.log.Timber

class MainActivity : AppCompatActivity(), LocationListener {
    private lateinit var binding: ActivityMainBinding
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            locationStart()
            Timber.d("locationが許可されました")
        } else {
            Timber.d("locationが拒否されました")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MainFragment>(binding.fragment.id)
        }

        val boot = AppLaunchChecker.hasStartedFromLauncher(applicationContext)
        if (!boot) {
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.guide_search))
                .setPositiveButton("Ok", null)
                .show()

            AppLaunchChecker.onActivityCreate(this)
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            locationStart()
        }
    }

    private fun locationStart() {
        Timber.d("locationStart()")

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER)
        ) {
            Timber.d("location manager Enabled")
        } else {
            val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(settingsIntent)
            Timber.d("not gpsEnable, startActivity")
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1000)
            Timber.d("checkSelfPermission false")
            return
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000,
            50f,
            this
        )
    }

    override fun onLocationChanged(location: Location) {
        ShopService.instance.location.postValue(location)
        Timber.d("緯度${location.latitude}, 経度${location.longitude}")
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

}