package com.nasa.nasasratsnew

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.preference.PreferenceManager
import com.google.android.material.appbar.AppBarLayout

class MainActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: Toolbar
    private lateinit var appBarLauout: AppBarLayout
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var imageNasa: ImageView
    private lateinit var navController: NavController
    private var languageRegion:String = "en"
    companion object {
        var isHDImage = false
        var language = "en"
        var twoText = false

        val twoTextDefault = false
        val languageDefault = "en"
        var isHDImageDefault = false

        val key_use_hd = "use_hd"
        val key_language = "language"
        val key_two_text = "show_two_texts"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        appBarLauout = findViewById(R.id.app_bar_layout)

        setSupportActionBar(toolbar)
        languageRegion = resources.getString(R.string.region)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        imageNasa = findViewById(R.id.nasa_start_image)
        imageNasa.visibility = View.INVISIBLE


        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_apod, R.id.nav_asteroids, R.id.nav_mars_photo,
                 R.id.nav_about_app
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        getPreferencesData()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_settings){
         //   navController.
            navController.navigate(R.id.to_fragment_preferences)


        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    


    private fun getPreferencesData(){
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        isHDImage = prefs.getBoolean(key_use_hd, isHDImageDefault)
        language = prefs.getString(key_language, "empty")!!
        if(language == "empty"){
            language = languageRegion
            val editor = prefs.edit()
            editor.putString(key_language, language)
            editor.apply()
        }
        twoText = prefs.getBoolean(key_two_text, twoTextDefault)
    }


}
