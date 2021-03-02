package jp.sfujiwara.githubuserssample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import dagger.hilt.android.AndroidEntryPoint
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.data.model.Repos
import jp.sfujiwara.githubuserssample.databinding.UserDetailFragmentBinding
import jp.sfujiwara.githubuserssample.ui.activity.FollowUserListActivity
import jp.sfujiwara.githubuserssample.ui.activity.FollowerUserListActivity
import jp.sfujiwara.githubuserssample.ui.adapter.OnCellClickListener
import jp.sfujiwara.githubuserssample.ui.adapter.ReposListAdapter
import jp.sfujiwara.githubuserssample.ui.view.MoreLoadRecyclerView
import jp.sfujiwara.githubuserssample.ui.viewmodel.UserDetailViewModel
import jp.sfujiwara.githubuserssample.util.IntentUtil


/**
 * Created by shn on 2021/02/26
 */
@AndroidEntryPoint
class UserDetailFragment : BaseFragment(), OnCellClickListener<Repos> {

    companion object {
        private const val LOGIN = "login"
        private const val AVATAR_URL = "avatar_url"
        private const val TRANSITION_NAME = "transition_name"
        fun newInstance(login: String?, avatarUrl: String?, transitionName: String?) =
            UserDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(LOGIN, login)
                    putString(AVATAR_URL, avatarUrl)
                    putString(TRANSITION_NAME, transitionName)
                }
            }
    }

    private val viewModel by viewModels<UserDetailViewModel>()
    private lateinit var binding: UserDetailFragmentBinding
    private val adapter = ReposListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_detail_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.userThumbnailImage.transitionName = arguments?.getString(TRANSITION_NAME)
        //Shared Element用にユーザー画像のみ前画面から流用する
        arguments?.getString(AVATAR_URL)?.let {
            binding.userThumbnailImage.load(it) {
                transformations(CircleCropTransformation())
            }
            binding.backgroundImage.load(it) {
                transformations(
                    BlurTransformation(
                        context = requireContext(),
                        radius = 5f,
                        sampling = 5f
                    )
                )
            }
        }
        viewInit()

        arguments?.getString(LOGIN)?.let {
            viewModel.init(it)
            viewModel.getUserDetail()
        }

        return binding.root
    }

    override fun onClick(value: Repos, targetView: View?) {
        value.htmlUrl?.let {
            IntentUtil.toWeb(requireContext(), it)
        }
    }

    private fun viewInit() {
        binding.recyclerview.setHasFixedSize(true)
        // アダプター設定
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

        // 追加読み込み用Listener設定
        binding.recyclerview.onMoreLoadLister = object : MoreLoadRecyclerView.OnMoreLoadLister {
            override fun onMoreLoad() {
                viewModel.moreLoad()
            }
        }

        // RecyclerView表示用LiveDataの更新をAdapterに検知させる
        viewModel.reposItems.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        // APIエラー表示用のSnaclbar
        viewModel.showMessageAction.observe(viewLifecycleOwner, Observer {
            showMessage(binding.root, it)
        })

        arguments?.getString(LOGIN)?.let { login ->
            binding.followParent.setOnClickListener(View.OnClickListener {
                startActivity(FollowUserListActivity.createIntent(requireContext(), login))
            })
            binding.followerParent.setOnClickListener(View.OnClickListener {
                startActivity(FollowerUserListActivity.createIntent(requireContext(), login))
            })
        }
    }
}