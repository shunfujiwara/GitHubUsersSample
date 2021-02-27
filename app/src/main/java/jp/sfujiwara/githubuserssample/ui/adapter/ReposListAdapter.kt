package jp.sfujiwara.githubuserssample.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.data.model.Repos
import jp.sfujiwara.githubuserssample.databinding.ViewReposListRowBinding
import jp.sfujiwara.githubuserssample.databinding.ViewUserListRowBinding
import jp.sfujiwara.githubuserssample.ui.adapter.holder.BindingViewHolder
import jp.sfujiwara.githubuserssample.ui.adapter.holder.RawViewHolder


/**
 * Created by shn on 2021/02/26
 */
class ReposListAdapter(
    private var items: List<Repos>,
    private var onCellClickListener: OnCellClickListener<Repos>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_REPOS_LIST = 1
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE_REPOS_LIST

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_REPOS_LIST -> BindingViewHolder<ViewReposListRowBinding>(
                parent, R.layout.view_repos_list_row
            )

            else -> RawViewHolder.newInstance(parent)
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RawViewHolder) {
            return
        }
        val repos = items[position]
        when ((holder as BindingViewHolder<*>).binding) {
            is ViewReposListRowBinding ->
                (holder.binding as ViewReposListRowBinding).also { it ->
                    it.repos = repos
                    it.root.setOnClickListener(View.OnClickListener {
                        onCellClickListener.onClick(repos, null)
                    })
                }
        }
    }

    fun setData(items: List<Repos>) {
        this.items = items
        notifyDataSetChanged()
    }
}