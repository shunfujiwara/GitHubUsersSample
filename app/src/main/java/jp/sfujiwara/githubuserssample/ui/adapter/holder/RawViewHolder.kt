package jp.sfujiwara.githubuserssample.ui.adapter.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.sfujiwara.githubuserssample.R


/**
 * Created by shn on 2021/02/27
 */
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