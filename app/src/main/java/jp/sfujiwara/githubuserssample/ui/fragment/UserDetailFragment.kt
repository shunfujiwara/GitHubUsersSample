package jp.sfujiwara.githubuserssample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.ChangeImageTransform
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet
import coil.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import dagger.hilt.android.AndroidEntryPoint
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.data.model.Repos
import jp.sfujiwara.githubuserssample.databinding.UserDetailFragmentBinding
import jp.sfujiwara.githubuserssample.ui.activity.MainActivity
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

    private val viewModel by viewModels<UserDetailViewModel>()
    private lateinit var binding: UserDetailFragmentBinding
    private val adapter = ReposListAdapter(arrayListOf(), this)
    private val navArgs by navArgs<UserDetailFragmentArgs>()
    override var screenName: String? = "User Detail"
    override var showHomeEnabled = false
    override var homeAsUpEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_detail_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        (requireActivity() as MainActivity).supportActionBar?.hide()

        binding.userThumbnailImage.transitionName = navArgs.transitionName
        //Shared Element用にユーザー画像のみ前画面から流用する
        binding.userThumbnailImage.load(navArgs.avatarUrl) {
            transformations(CircleCropTransformation())
        }
        binding.backgroundImage.load(navArgs.avatarUrl) {
            transformations(
                BlurTransformation(
                    context = requireContext(),
                    radius = 5f,
                    sampling = 5f
                )
            )
            //SharedElementアニメーション設定
            val transition = TransitionSet().apply {
                addTransition(ChangeTransform())
                addTransition(ChangeImageTransform())
            }
            sharedElementEnterTransition = transition
            sharedElementReturnTransition = transition
        }
        viewInit()

        viewModel.init(navArgs.login)
        viewModel.getUserDetail()

        return binding.root
    }

    override fun onClick(value: Repos, targetView: View?) {
        value.htmlUrl?.let {
            IntentUtil.toWeb(requireContext(), it)
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

        // 追加読み込み用Listener設定
        binding.recyclerview.onMoreLoadLister = object : MoreLoadRecyclerView.OnMoreLoadLister {
            override fun onMoreLoad() {
                viewModel.moreLoad()
            }
        }

        // RecyclerView表示用LiveDataの更新をAdapterに検知させる
        viewModel.reposItems.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        // APIエラー表示用のSnaclbar
        viewModel.showMessageAction.observe(viewLifecycleOwner, Observer {
            showMessage(binding.root, it)
        })

        binding.followParent.setOnClickListener(View.OnClickListener {
            val action = UserDetailFragmentDirections.actionDetailToFollowing(
                login = navArgs.login
            )
            findNavController().navigate(action)
        })
        binding.followerParent.setOnClickListener(View.OnClickListener {
            val action = UserDetailFragmentDirections.actionDetailToFollower(
                login = navArgs.login
            )
            findNavController().navigate(action)
        })
    }
}