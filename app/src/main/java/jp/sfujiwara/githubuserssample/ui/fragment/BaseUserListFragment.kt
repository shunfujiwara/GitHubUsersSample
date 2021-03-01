package jp.sfujiwara.githubuserssample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.data.model.User
import jp.sfujiwara.githubuserssample.databinding.UserListFragmentBinding
import jp.sfujiwara.githubuserssample.ui.activity.MainActivity
import jp.sfujiwara.githubuserssample.ui.adapter.OnCellClickListener
import jp.sfujiwara.githubuserssample.ui.adapter.UserListAdapter


/**
 * Created by shn on 2021/02/27
 */
abstract class BaseUserListFragment : BaseFragment(), OnCellClickListener<User> {

    protected val adapter = UserListAdapter(arrayListOf(), this)
    protected lateinit var binding: UserListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_list_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewInit()

        // Detailからの戻りのアニメーションを行うためにRecyclerViewの描画を待つ
        postponeEnterTransition()
        binding.root.viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.show()
    }

    override fun onClick(user: User, targetView: View?) {
        targetView?.let {
            user.login?.let {
                val extras = FragmentNavigatorExtras(
                    targetView to targetView.transitionName
                )
                val action = when (findNavController().currentDestination?.id) {
                    R.id.user_list -> UserListFragmentDirections.actionListToDetail(
                        login = it,
                        avatarUrl = user.avatarUrl ?: "",
                        transitionName = targetView.transitionName
                    )
                    R.id.user_following -> FollowUserListFragmentDirections.actionFollowingToDetail(
                        login = it,
                        avatarUrl = user.avatarUrl ?: "",
                        transitionName = targetView.transitionName
                    )
                    else -> FollowerUserListFragmentDirections.actionFollowerToDetail(
                        login = it,
                        avatarUrl = user.avatarUrl ?: "",
                        transitionName = targetView.transitionName
                    )
                }
                findNavController().navigate(action, extras)
            }
        }
    }

    private fun viewInit() {
        // アダプター設定
        binding.recyclerview.adapter = adapter

        // RecyclerViewのDivider
        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerview.context,
            LinearLayoutManager(activity).orientation
        )
        ContextCompat.getDrawable(requireContext(), R.drawable.shape_divider)?.let { drawable ->
            dividerItemDecoration.setDrawable(drawable)
        }
        binding.recyclerview.addItemDecoration(dividerItemDecoration)
    }
}