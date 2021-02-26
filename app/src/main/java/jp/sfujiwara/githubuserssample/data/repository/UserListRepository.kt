package jp.sfujiwara.githubuserssample.data.repository

import jp.sfujiwara.githubuserssample.data.ApiService
import jp.sfujiwara.githubuserssample.data.model.Resource
import jp.sfujiwara.githubuserssample.data.model.User
import javax.inject.Inject


/**
 * Created by shn on 2021/02/26
 */
class UserListRepository @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun getUsers(perPage: Int, since: Int): Resource<List<User>>? {
        return apiService.getUsers(perPage, since)
    }

}