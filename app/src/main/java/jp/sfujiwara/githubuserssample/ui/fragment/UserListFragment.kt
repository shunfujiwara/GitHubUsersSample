package jp.sfujiwara.githubuserssample.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.data.model.User
import jp.sfujiwara.githubuserssample.databinding.UserListFragmentBinding
import jp.sfujiwara.githubuserssample.ui.adapter.UserListAdapter
import jp.sfujiwara.githubuserssample.ui.view.MoreLoadRecyclerView
import jp.sfujiwara.githubuserssample.ui.viewmodel.UserListViewModel

@AndroidEntryPoint
class UserListFragment : Fragment(), UserListAdapter.OnCellClickListener {

    companion object {
        fun newInstance() =
            UserListFragment()
    }

    private val viewModel by viewModels<UserListViewModel>()
    private lateinit var lifecycleObserver: LifecycleObserver
    private lateinit var binding: UserListFragmentBinding

    private val adapter = UserListAdapter(arrayListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_list_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = this.viewModel

        viewInit()
        viewModel.getUsers()

        return binding.root
    }

    override fun onClick(user: User) {
    }

    private fun viewInit() {
        // アダプター設定
        binding.recyclerview.adapter = adapter

        // RecyclerViewのDivider
        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerview.context,
            LinearLayoutManager(activity).orientation
        )
        context?.let { it ->
            ContextCompat.getDrawable(it, R.drawable.shape_divider)?.let { drawable ->
                dividerItemDecoration.setDrawable(drawable)
            }
        }
        binding.recyclerview.addItemDecoration(dividerItemDecoration)

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
//        viewModel.showMessageAction.observe(viewLifecycleOwner, Observer {
//            showMessage(binding.root, it)
//        })
    }
}