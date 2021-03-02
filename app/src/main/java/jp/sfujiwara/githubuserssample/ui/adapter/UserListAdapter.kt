package jp.sfujiwara.githubuserssample.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.data.model.User
import jp.sfujiwara.githubuserssample.databinding.ViewUserListRowBinding
import jp.sfujiwara.githubuserssample.ui.adapter.holder.BindingViewHolder
import jp.sfujiwara.githubuserssample.ui.adapter.holder.RawViewHolder


/**
 * Created by shn on 2021/02/26
 */
class UserListAdapter(
    private var items: List<User>,
    private var onCellClickListener: OnCellClickListener<User>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_USER_LIST = 1
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE_USER_LIST

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER_LIST -> BindingViewHolder<ViewUserListRowBinding>(
                parent, R.layout.view_user_list_row
            )

            else -> RawViewHolder.newInstance(parent)
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RawViewHolder) {
            return
        }
        val user = items[position]
        when ((holder as BindingViewHolder<*>).binding) {
            is ViewUserListRowBinding ->
                (holder.binding as ViewUserListRowBinding).also { it ->
                    bindBigImageRow(user, it, position)
                }
        }
    }

    fun setData(items: List<User>) {
        this.items = items
        notifyDataSetChanged()
    }

    private fun bindBigImageRow(user: User, binding: ViewUserListRowBinding, position: Int) {
        binding.user = user
        binding.clickArea.setOnClickListener(View.OnClickListener {
            onCellClickListener.onClick(user, binding.userThumbnailImage)
        })
        binding.userThumbnailImage.transitionName = position.toString()
    }
}