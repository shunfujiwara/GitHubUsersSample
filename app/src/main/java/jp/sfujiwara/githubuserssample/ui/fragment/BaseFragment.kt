package jp.sfujiwara.githubuserssample.ui.fragment

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import jp.sfujiwara.githubuserssample.ui.activity.MainActivity


/**
 * Created by shn on 2021/02/27
 */
abstract class BaseFragment : Fragment() {

    abstract var screenName: String?
    abstract var homeAsUpEnabled: Boolean
    abstract var showHomeEnabled: Boolean


    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.let {
            it.title = screenName
            it.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
            it.setDisplayShowHomeEnabled(showHomeEnabled)
        }
    }

    protected fun showMessage(targetView: View, message: String) {
        Snackbar.make(targetView, message, BaseTransientBottomBar.LENGTH_LONG).show()
    }
}