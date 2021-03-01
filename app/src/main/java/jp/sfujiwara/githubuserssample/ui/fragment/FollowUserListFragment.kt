package jp.sfujiwara.githubuserssample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.ui.view.MoreLoadRecyclerView
import jp.sfujiwara.githubuserssample.ui.viewmodel.FollowUserListViewModel


@AndroidEntryPoint
class FollowUserListFragment : BaseUserListFragment() {

    companion object {
        private const val LOGIN = "login"
        fun newInstance(login: String) =
            FollowUserListFragment().apply {
                arguments = Bundle().apply {
                    putString(LOGIN, login)
                }
            }
    }

    private val viewModel by viewModels<FollowUserListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        viewModelInit()

        val login = arguments?.getString(LOGIN)
        if (login.isNullOrEmpty()) {
            showMessage(binding.root, getString(R.string.error_message))
        } else {
            viewModel.init(login)
            viewModel.getFollowUsers()
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
            adapter.submitList(it)
        })

        // APIエラー表示用のSnaclbar
        viewModel.showMessageAction.observe(viewLifecycleOwner, Observer {
            showMessage(binding.root, it)
        })
    }
}