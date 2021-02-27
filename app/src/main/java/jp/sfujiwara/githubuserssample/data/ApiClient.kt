package jp.sfujiwara.githubuserssample.data

import jp.sfujiwara.githubuserssample.data.model.Repos
import jp.sfujiwara.githubuserssample.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by shn on 2021/02/26
 */
interface ApiClient {

    @GET("/users")
    suspend fun getUsers(
        @Query("per_page") perPage: Int, @Query("since") since: Int
    ): Response<List<User>>

    @GET("/users/{login}")
    suspend fun getUserDetail(@Path("login") name: String?): Response<User>

    @GET("/users/{login}/repos")
    suspend fun getUserRepos(@Path("login") name: String?, @Query("per_page") perPage: Int, @Query("page") page: Int): Response<List<Repos>>

    @GET("/users/{login}/following")
    suspend fun getFollowUsers(@Path("login") name: String?, @Query("per_page") perPage: Int, @Query("page") page: Int): Response<List<User>>


    @GET("/users/{login}/followers")
    suspend fun getFollowerUsers(@Path("login") name: String?, @Query("per_page") perPage: Int, @Query("page") page: Int): Response<List<User>>

}