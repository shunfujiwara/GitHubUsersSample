package jp.sfujiwara.githubuserssample.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * 自動追加読み込みするRecyclerView
 * onMoreLoadListerを設定することでonMoreLoadに発火する
 * Created by shn on 2021/02/26
 */
class MoreLoadRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {


    var onMoreLoadLister: OnMoreLoadLister? = null

    init {
        this.addOnScrollListener(InfiniteScrollListener())
    }

    /**
     * 自動追加読み込み用のOnScrollListener
     */
    inner class InfiniteScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            // AutoLoadMore
            val itemCount = adapter?.itemCount
            val childCount = recyclerView.childCount
            val manager = recyclerView.layoutManager as LinearLayoutManager
            val firstPosition = manager.findFirstVisibleItemPosition()

            if (itemCount == childCount + firstPosition + 1) {
                onMoreLoadLister?.onMoreLoad()
            }
        }
    }

    interface OnMoreLoadLister {
        fun onMoreLoad()
    }
}