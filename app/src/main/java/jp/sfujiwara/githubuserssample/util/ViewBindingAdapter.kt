package jp.sfujiwara.githubuserssample.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import jp.sfujiwara.githubuserssample.R


/**
 * Created by shn on 2021/02/26
 */
object ViewBindingAdapter {

    @BindingAdapter("load_circleImage")
    @JvmStatic
    fun ImageView.setLoadUrl(url: String?) {
        url?.let {
            load(it) {
                transformations(CircleCropTransformation())
            }
        }
    }

//@BindingAdapter("app:bind_url")
//fun SimpleDraweeView.setUrl(url: String?) {
//    url?.let {
//        try {
//            setImageURI(it)
//        } catch (e: Exception) {
//            Timber.e(e, "app:bind_url")
//        }
//    }
//}
}