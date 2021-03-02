package jp.sfujiwara.githubuserssample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import jp.sfujiwara.githubuserssample.ui.view.MoreLoadRecyclerView
import jp.sfujiwara.githubuserssample.ui.viewmodel.FollowUserListViewModel


@AndroidEntryPoint
class FollowUserListFragment : BaseUserListFragment() {

    private val viewModel by viewModels<FollowUserListViewModel>()
    private val navArgs by navArgs<FollowUserListFragmentArgs>()
    override var screenName: String? = "Following"
    override var showHomeEnabled = true
    override var homeAsUpEnabled = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        viewModelInit()

        viewModel.init(navArgs.login)
        if (viewModel.userItems.value.isNullOrEmpty()) {
            viewModel.getFollowUsers()
        }
        return view
    }

    override fun onResume() {
        val login = navArgs.login
        screenName = "$login Following"
        super.onResume()
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