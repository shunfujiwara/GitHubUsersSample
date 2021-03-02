package jp.sfujiwara.githubuserssample.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import timber.log.Timber
import java.lang.Exception


/**
 * Created by shn on 2021/02/27
 */
object IntentUtil{

    /**
     * 外部ブラウザを開くインテントを作成する
     */
    fun toWeb(context: Context, url: String) {
        try {
            val intent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .build()
            intent.intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.launchUrl(context, url.toUri())
        } catch (ignore: ActivityNotFoundException) {
            Timber.e(ignore)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}