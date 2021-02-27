package jp.sfujiwara.githubuserssample.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.ui.fragment.UserDetailFragment


/**
 * Created by shn on 2021/02/26
 */
@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    companion object {
        private const val LOGIN = "login"
        private const val AVATAR_URL = "avatar_url"
        private const val TRANSITION_NAME = "transition_name"
        fun createIntent(
            context: Context,
            login: String,
            avatarUrl: String?,
            transitionName: String
        ) = Intent(
            context, UserDetailActivity::class.java
        ).also {
            it.putExtra(LOGIN, login)
            it.putExtra(AVATAR_URL, avatarUrl)
            it.putExtra(TRANSITION_NAME, transitionName)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_detail_activity)
        if (savedInstanceState == null) {
            val login = intent.getStringExtra(LOGIN)
            val avatarUrl = intent.getStringExtra(AVATAR_URL)
            val transitionName = intent.getStringExtra(TRANSITION_NAME)
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    UserDetailFragment.newInstance(login, avatarUrl, transitionName)
                )
                .commitNow()
        }
    }
}