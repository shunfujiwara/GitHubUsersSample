package jp.sfujiwara.githubuserssample.data.repository

import jp.sfujiwara.githubuserssample.data.ApiService
import jp.sfujiwara.githubuserssample.data.model.Resource
import jp.sfujiwara.githubuserssample.data.model.User
import javax.inject.Inject


/**
 * Created by shn on 2021/02/26
 */
class FollowUserRepository @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun getFollowUsers(login: String, perPage: Int, page: Int): Resource<List<User>>? {
        return apiService.getFollowUsers(login, perPage, page)
    }
}