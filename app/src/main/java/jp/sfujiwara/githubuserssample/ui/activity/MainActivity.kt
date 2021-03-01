package jp.sfujiwara.githubuserssample.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import jp.sfujiwara.githubuserssample.R


/**
 * Created by shn on 2021/03/01
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            navHost = NavHostFragment.create(R.navigation.mobile_navigation)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, navHost, null)
                .commitNow()
        }
    }

    override fun onBackPressed() {
        if (navHost.navController.currentDestination?.id == R.id.user_list) {
            finish()
            return
        }
        navHost.navController.popBackStack()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}