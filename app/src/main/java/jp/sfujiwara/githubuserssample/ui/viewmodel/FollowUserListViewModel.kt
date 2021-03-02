package jp.sfujiwara.githubuserssample.ui.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.sfujiwara.githubuserssample.data.model.Resource
import jp.sfujiwara.githubuserssample.data.model.User
import jp.sfujiwara.githubuserssample.data.repository.FollowUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FollowUserListViewModel @Inject constructor(private val repository: FollowUserRepository) :
    ViewModel() {

    // SnackBar表示ようにLiveData
    val showMessageAction = MutableLiveData<String>()

    private val usersRaw = mutableListOf<User>()
    private val _userItems = MutableLiveData<List<User>>(emptyList())
    val userItems: LiveData<List<User>> = _userItems.distinctUntilChanged()
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
    fun getFollowUsers() {
        isLoading = true
        page++

        viewModelScope.launch {
            //APIを非同期でキック
            val result = async(Dispatchers.IO) {
                runCatching {
                    repository.getFollowUsers(login, 100, page)
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
                        usersRaw.addAll(it.data.toMutableList())
                        _userItems.value = ArrayList(usersRaw)
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
        getFollowUsers()
    }
}