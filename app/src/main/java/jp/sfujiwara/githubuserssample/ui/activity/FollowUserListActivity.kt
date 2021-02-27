package jp.sfujiwara.githubuserssample.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import dagger.hilt.android.AndroidEntryPoint
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.ui.fragment.FollowUserListFragment


/**
 * Created by shn on 2021/02/27
 */
@AndroidEntryPoint
class FollowUserListActivity : AppCompatActivity() {

    companion object {
        private const val LOGIN = "login"
        fun createIntent(context: Context, login: String) = Intent(
            context, FollowUserListActivity::class.java
        ).also {
            it.putExtra(LOGIN, login)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.follow_user_list_activity)

        if (savedInstanceState == null) {
            val login = intent.getStringExtra(LOGIN)

            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            setSupportActionBar(toolbar)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowHomeEnabled(true)
                it.title = "$login following"
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FollowUserListFragment.newInstance(login))
                .commitNow()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}