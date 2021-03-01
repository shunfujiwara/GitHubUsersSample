package jp.sfujiwara.githubuserssample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import jp.sfujiwara.githubuserssample.ui.view.MoreLoadRecyclerView
import jp.sfujiwara.githubuserssample.ui.viewmodel.UserListViewModel


@AndroidEntryPoint
class UserListFragment : BaseUserListFragment() {

    private val viewModel by viewModels<UserListViewModel>()
    override var screenName: String? = "GitHub Users"
    override var showHomeEnabled = false
    override var homeAsUpEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        viewModelInit()
        if (viewModel.userItems.value.isNullOrEmpty()) {
            viewModel.getUsers()
        }
        return view
    }

    private fun viewModelInit() {
        // 追加読み込み用Listener設定
        binding.recyclerview.onMoreLoadLister = object : MoreLoadRecyclerView.OnMoreLoadLister {
            override fun onMoreLoad() {
                viewModel.moreLoad()
            }
        }

        // RecyclerView表示用LiveDataの更新をAdapterに検知させる
        viewModel.userItems.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        // APIエラー表示用のSnaclbar
        viewModel.showMessageAction.observe(viewLifecycleOwner, Observer {
            showMessage(binding.root, it)
        })
    }
}