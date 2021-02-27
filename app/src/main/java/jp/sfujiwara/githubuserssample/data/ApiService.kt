package jp.sfujiwara.githubuserssample.data

import javax.inject.Inject


/**
 * Created by shn on 2021/02/26
 */
class ApiService @Inject constructor(
    private val apiClient: ApiClient
) : BaseApiService() {

    suspend fun getUsers(perPage: Int, since: Int) =
        getResult {
            apiClient.getUsers(perPage, since)
        }

    suspend fun getUserDetail(login: String) =
        getResult {
            apiClient.getUserDetail(login)
        }

    suspend fun getUserRepos(login: String, perPage: Int, page: Int) =
        getResult {
            apiClient.getUserRepos(login, perPage, page)
        }

    suspend fun getFollowUsers(login: String, perPage: Int, page: Int) =
        getResult {
            apiClient.getFollowUsers(login, perPage, page)
        }

    suspend fun getFollowerUsers(login: String, perPage: Int, page: Int) =
        getResult {
            apiClient.getFollowerUsers(login, perPage, page)
        }
}