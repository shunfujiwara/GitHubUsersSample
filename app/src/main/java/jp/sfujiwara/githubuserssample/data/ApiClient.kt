package jp.sfujiwara.githubuserssample.data

import jp.sfujiwara.githubuserssample.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by shn on 2021/02/26
 */
interface ApiClient {

    @GET("/users")
    suspend fun getUsers(
        @Query("per_page") perPage: Int, @Query("since") since: Int
    ): Response<List<User>>

}