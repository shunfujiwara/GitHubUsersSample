package jp.sfujiwara.githubuserssample.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.sfujiwara.githubuserssample.data.repository.UserListRepository
import javax.inject.Inject

@HiltViewModel
class UserListViewModel@Inject constructor(private val repository: UserListRepository): ViewModel() {
    // TODO: Implement the ViewModel
}