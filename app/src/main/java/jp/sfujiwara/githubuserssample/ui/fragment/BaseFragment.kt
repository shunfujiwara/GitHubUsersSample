package jp.sfujiwara.githubuserssample.ui.fragment

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


/**
 * Created by shn on 2021/02/27
 */
abstract class BaseFragment : Fragment() {

    protected fun showMessage(targetView: View, message: String) {
        Snackbar.make(targetView, message, BaseTransientBottomBar.LENGTH_LONG).show()
    }
}