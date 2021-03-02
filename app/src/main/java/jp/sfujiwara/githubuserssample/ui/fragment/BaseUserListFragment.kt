package jp.sfujiwara.githubuserssample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.data.model.User
import jp.sfujiwara.githubuserssample.databinding.UserListFragmentBinding
import jp.sfujiwara.githubuserssample.ui.activity.UserDetailActivity
import jp.sfujiwara.githubuserssample.ui.adapter.OnCellClickListener
import jp.sfujiwara.githubuserssample.ui.adapter.UserListAdapter


/**
 * Created by shn on 2021/02/27
 */
abstract class BaseUserListFragment : BaseFragment(), OnCellClickListener<User> {

    protected val adapter = UserListAdapter(this)
    protected lateinit var binding: UserListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_list_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewInit()

        return binding.root
    }

    override fun onClick(value: User, targetView: View?) {
        targetView?.let {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                it,
                it.transitionName
            )
            value.login?.let {
                val intent = UserDetailActivity.createIntent(
                    requireContext(),
                    it,
                    value.avatarUrl,
                    targetView.transitionName
                )
                startActivity(intent, options.toBundle())
            }
        }
    }

    private fun viewInit() {
        // アダプター設定
        binding.recyclerview.setHasFixedSize(true)
        adapter.setHasStableIds(true)
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