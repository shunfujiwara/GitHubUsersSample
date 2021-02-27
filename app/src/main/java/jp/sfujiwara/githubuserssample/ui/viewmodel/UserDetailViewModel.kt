package jp.sfujiwara.githubuserssample.ui.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.sfujiwara.githubuserssample.data.model.Repos
import jp.sfujiwara.githubuserssample.data.model.Resource
import jp.sfujiwara.githubuserssample.data.model.User
import jp.sfujiwara.githubuserssample.data.repository.UserDetailRepository
import jp.sfujiwara.githubuserssample.data.repository.UserListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel@Inject constructor(private val repository: UserDetailRepository): ViewModel() {

    // SnackBar表示ようにLiveData
    val showMessageAction = MutableLiveData<String>()
    val userDetail = MutableLiveData<User>()
    val reposItems = MutableLiveData<List<Repos>>()

    private var isLoading = false
    private var isLoadAll = false
    private var page = 0
    private lateinit var login: String


    fun init(login: String) {
        this.login = login
    }

    /**
     * APIからTimelineを取得する
     */
    fun getUserDetail() {

        viewModelScope.launch {
            //APIを非同期でキック
            val result = async(Dispatchers.IO) {
                runCatching {
                    repository.getUserDetail(login)
                }
            }.await()

            //結果取得
            result
                .onSuccess { it ->
                    if (it?.status == Resource.Status.SUCCESS) {
                        if (it.data == null) {
                            return@onSuccess
                        }
                        userDetail.value = it.data!!

                    } else if (it?.status == Resource.Status.NOT_FOUND) {
                        //TODO NOTFOUND
                    } else {
                        // エラー
                        val errorMessage = if (it?.message != null) {
                            it.message
                        } else {
                            "予期せぬエラーが発生しました"
                        }
                        showMessageAction.value = errorMessage
                    }
                }
                .onFailure {
                    Timber.e(it)
                    showMessageAction.value = it.message
                }
        }
    }

    fun getRepos() {
        page++
        isLoading = true

        viewModelScope.launch {
            //APIを非同期でキック
            val result = async(Dispatchers.IO) {
                runCatching {
                    repository.getUserRepos(login, 100, page)
                }
            }.await()

            //結果取得
            result
                .onSuccess { it ->
                    if (it?.status == Resource.Status.SUCCESS) {
                        if (it.data.isNullOrEmpty()) {
                            isLoadAll = true
                            return@onSuccess
                        }
                        if (!reposItems.value.isNullOrEmpty()) {
                            // 既にデータがあれば結合する
                            val last = reposItems.value!!.toMutableList()
                            reposItems.value = last.plus(it.data.toMutableList())
                        } else {
                            // データがなければ代入
                            reposItems.value = it.data!!
                        }
                        Timber.d("COUNT = " + reposItems?.value?.size)
                    } else if (it?.status == Resource.Status.NOT_FOUND) {
                        // すべて読み込んだと判定する
                        isLoadAll = true
                    } else {
                        // エラー
                        val errorMessage = if (it?.message != null) {
                            it.message
                        } else {
                            "予期せぬエラーが発生しました"
                        }
                        showMessageAction.value = errorMessage
                    }
                    isLoading = false
                }
                .onFailure {
                    Timber.e(it)
                    showMessageAction.value = it.message
                    isLoading = false
                }
        }
    }

    /**
     * 現在表示しているページを次ページを表示する
     */
    fun moreLoad() {
        // 読込中or全読み込み後はSkip
        if (isLoadAll || isLoading) {
            return
        }
        getRepos()
    }
}