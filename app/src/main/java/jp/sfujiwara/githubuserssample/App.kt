package jp.sfujiwara.githubuserssample

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


/**
 * Created by shn on 2021/02/26
 */
@HiltAndroidApp
class App: Application(){
    override fun onCreate() {
        super.onCreate()

        // Logger
        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(AndroidLogAdapter())
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    Logger.log(priority, tag, message, t)
                }
            })
        }
    }
}