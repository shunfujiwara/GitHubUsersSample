package jp.sfujiwara.githubuserssample

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import timber.log.Timber


/**
 * Created by shn on 2021/02/26
 */
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        ImageLoader(applicationContext)

        val imageLoader = ImageLoader.Builder(applicationContext)
            .crossfade(true)
            .placeholder(R.drawable.place_holder)
            .availableMemoryPercentage(0.1)
            .bitmapPoolPercentage(0.1)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(applicationContext))
                    .build()
            }
            .build()
        Coil.setImageLoader(imageLoader)

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