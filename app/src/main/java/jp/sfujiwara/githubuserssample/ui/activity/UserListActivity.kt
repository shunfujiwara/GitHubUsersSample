package jp.sfujiwara.githubuserssample.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import dagger.hilt.android.AndroidEntryPoint
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.ui.fragment.UserListFragment

@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list_activity)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.title = "GitHub Users"
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UserListFragment.newInstance())
                .commitNow()
        }
    }
}