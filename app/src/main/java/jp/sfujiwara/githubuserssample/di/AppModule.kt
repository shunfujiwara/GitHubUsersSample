package jp.sfujiwara.githubuserssample.di

import android.content.Context
import androidx.databinding.library.BuildConfig
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.data.ApiClient
import jp.sfujiwara.githubuserssample.data.ApiRequestInterceptor
import jp.sfujiwara.githubuserssample.data.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by shn on 2021/02/26
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpClient(
        @ApplicationContext context: Context,
        interceptor: ApiRequestInterceptor
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(logging)
            .cache(CoilUtils.createDefaultCache(context))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        @ApplicationContext context: Context,
        client: OkHttpClient,
        gSon: Gson
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(context.getString(R.string.api_domain))
        .addConverterFactory(GsonConverterFactory.create(gSon))
        .build()

    @Provides
    fun provideGSon(): Gson = GsonBuilder().create()

    @Provides
    fun provideApiClient(retrofit: Retrofit): ApiClient = retrofit.create(ApiClient::class.java)

    @Singleton
    @Provides
    fun provideApiService(apiClient: ApiClient) = ApiService(apiClient)

    @Singleton
    @Provides
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        val imageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(context))
                    .build()
            }
            .build()
        Coil.setImageLoader(imageLoader)

        return imageLoader
    }

}