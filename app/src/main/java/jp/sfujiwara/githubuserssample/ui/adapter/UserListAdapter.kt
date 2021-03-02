package jp.sfujiwara.githubuserssample.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.data.model.User
import jp.sfujiwara.githubuserssample.databinding.ViewUserListRowBinding
import jp.sfujiwara.githubuserssample.ui.adapter.holder.BindingViewHolder


/**
 * Created by shn on 2021/02/26
 */
class UserListAdapter(
    private var onCellClickListener: OnCellClickListener<User>
) : ListAdapter<User, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BindingViewHolder<ViewUserListRowBinding>(
            parent, R.layout.view_user_list_row
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = getItem(position)
        when ((holder as BindingViewHolder<*>).binding) {
            is ViewUserListRowBinding ->
                (holder.binding as ViewUserListRowBinding).also { it ->
                    bindUserRow(user, it, position)
                }
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    private fun bindUserRow(user: User, binding: ViewUserListRowBinding, position: Int) {
        binding.user = user
        binding.root.setOnClickListener(View.OnClickListener {
            onCellClickListener.onClick(user, binding.userThumbnailImage)
        })
        binding.userThumbnailImage.transitionName = position.toString()
        binding.executePendingBindings()
    }

    private object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}