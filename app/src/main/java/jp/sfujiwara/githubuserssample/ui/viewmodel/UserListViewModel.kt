package jp.sfujiwara.githubuserssample.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.sfujiwara.githubuserssample.data.model.Resource
import jp.sfujiwara.githubuserssample.data.model.User
import jp.sfujiwara.githubuserssample.data.repository.UserListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val repository: UserListRepository) :
    ViewModel() {

    // SnackBar表示ようにLiveData
    val showMessageAction = MutableLiveData<String>()

    val userItems = MutableLiveData<List<User>>()
    private var isLoading = false
    private var isLoadAll = false
    private var lastId = 0


    /**
     * APIからTimelineを取得する
     */
    fun getUsers() {
        isLoading = true

        viewModelScope.launch {
            //APIを非同期でキック
            val result = async(Dispatchers.IO) {
                runCatching {
                    repository.getUsers(100, lastId)
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
                        if (!userItems.value.isNullOrEmpty()) {
                            // 既にデータがあれば結合する
                            val last = userItems.value!!.toMutableList()
                            userItems.value = last.plus(it.data.toMutableList())
                        } else {
                            // データがなければ代入
                            userItems.value = it.data!!
                        }
                        lastId = userItems.value?.last()?.id ?: 0
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
        getUsers()
    }
}