package jp.sfujiwara.githubuserssample.data

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by shn on 2021/02/26
 */
class ApiRequestInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request()
            .newBuilder()

        val request = builder.build()
        val response = chain.proceed(request)
        if (response.cacheResponse() != null) {
            // from cache
            Timber.d("is cache")
        } else if (response.networkResponse() != null) {
            // from network
            Timber.d("is network")
        }

        Timber.d("Code = ${response.code()}")

        return response
    }
}
