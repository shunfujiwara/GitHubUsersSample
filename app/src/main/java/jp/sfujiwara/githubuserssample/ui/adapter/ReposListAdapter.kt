package jp.sfujiwara.githubuserssample.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.sfujiwara.githubuserssample.R
import jp.sfujiwara.githubuserssample.data.model.Repos
import jp.sfujiwara.githubuserssample.databinding.ViewReposListRowBinding
import jp.sfujiwara.githubuserssample.ui.adapter.holder.BindingViewHolder


/**
 * Created by shn on 2021/02/26
 */
class ReposListAdapter(
    private var onCellClickListener: OnCellClickListener<Repos>
) : ListAdapter<Repos, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BindingViewHolder<ViewReposListRowBinding>(
            parent, R.layout.view_repos_list_row
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repos = getItem(position)
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

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    private object DiffCallback : DiffUtil.ItemCallback<Repos>() {
        override fun areItemsTheSame(oldItem: Repos, newItem: Repos): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repos, newItem: Repos): Boolean {
            return oldItem == newItem
        }
    }
}