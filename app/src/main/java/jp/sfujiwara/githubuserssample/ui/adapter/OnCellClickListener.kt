package jp.sfujiwara.githubuserssample.ui.adapter

import android.view.View
import jp.sfujiwara.githubuserssample.data.model.User


/**
 * Created by shn on 2021/02/27
 */
interface OnCellClickListener<T> {
    fun onClick(value: T, targetView: View?)
}