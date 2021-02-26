package jp.sfujiwara.githubuserssample.data

import com.google.gson.Gson
import jp.sfujiwara.githubuserssample.data.model.Resource
import jp.sfujiwara.githubuserssample.data.response.ErrorResponse
import okhttp3.ResponseBody
import retrofit2.Response


/**
 * Created by shn on 2021/02/26
 */
abstract class BaseApiService {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            if (response.code() == 404) {
                return Resource.notFound()
            }
            return error(response.errorBody())
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorBody: ResponseBody?): Resource<T> {
        val errorResponse = Gson().fromJson<ErrorResponse>(errorBody?.string(), ErrorResponse::class.java)
        return Resource.error(errorResponse.error ?: "通信中にエラーが発生しました。")
    }
}