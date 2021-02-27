package jp.sfujiwara.githubuserssample.data.repository

import jp.sfujiwara.githubuserssample.data.ApiService
import jp.sfujiwara.githubuserssample.data.model.Repos
import jp.sfujiwara.githubuserssample.data.model.Resource
import jp.sfujiwara.githubuserssample.data.model.User
import javax.inject.Inject


/**
 * Created by shn on 2021/02/26
 */
class UserDetailRepository @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun getUserDetail(login: String): Resource<User>? {
        return apiService.getUserDetail(login)
    }

    suspend fun getUserRepos(login: String, perPage: Int, page: Int): Resource<List<Repos>>? {
        return apiService.getUserRepos(login, perPage, page)
    }
}