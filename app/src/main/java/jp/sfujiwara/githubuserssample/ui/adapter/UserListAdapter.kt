package jp.sfujiwara.githubuserssample.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.data.model.User
import jp.sfujiwara.githubuserssample.databinding.ViewUserListRowBinding
import jp.sfujiwara.githubuserssample.ui.adapter.holder.BindingViewHolder


/**
 * Created by shn on 2021/02/26
 */
class UserListAdapter(
    private var items: List<User>,
    private var onCellClickListener: OnCellClickListener
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
                    bindBigImageRow(user, it)
                }
        }
        holder.binding.root.setOnClickListener(View.OnClickListener {
            onCellClickListener.onClick(user)
        })
    }

    fun setData(items: List<User>) {
        this.items = items
        notifyDataSetChanged()
    }

    /**
     * Type = BigImageセルへのDataBind
     */
    private fun bindBigImageRow(user: User, binding: ViewUserListRowBinding) {
        binding.user = user
        binding.userThumbnailImage.load(user.avatarUrl) {
            crossfade(true)
            placeholder(R.drawable.place_holder)
            transformations(CircleCropTransformation())
        }
    }

    class RawViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun newInstance(parent: ViewGroup): RawViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.view_space, parent, false
                    )
                return RawViewHolder(view)
            }
        }
    }

    interface OnCellClickListener {
        fun onClick(user: User)
    }
}