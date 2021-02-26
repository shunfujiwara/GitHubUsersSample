package jp.sfujiwara.githubuserssample.data.repository

import jp.sfujiwara.githubuserssample.data.ApiService
import javax.inject.Inject


/**
 * Created by shn on 2021/02/26
 */
class UserListRepository @Inject constructor(
    private val apiService: ApiService,
) {
}