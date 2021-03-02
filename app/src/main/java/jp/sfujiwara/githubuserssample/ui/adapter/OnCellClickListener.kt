package jp.sfujiwara.githubuserssample.ui.adapter

import android.view.View


/**
 * Created by shn on 2021/02/27
 */
interface OnCellClickListener<T> {
    fun onClick(value: T, targetView: View?)
}