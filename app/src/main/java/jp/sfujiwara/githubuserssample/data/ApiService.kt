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
}