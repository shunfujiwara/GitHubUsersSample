package jp.sfujiwara.githubuserssample.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.databinding.UserListFragmentBinding
import jp.sfujiwara.githubuserssample.ui.viewmodel.UserListViewModel

@AndroidEntryPoint
class UserListFragment : Fragment() {

    companion object {
        fun newInstance() =
            UserListFragment()
    }

    private val viewModel by viewModels<UserListViewModel>()
    private lateinit var binding: UserListFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_list_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = this.viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}