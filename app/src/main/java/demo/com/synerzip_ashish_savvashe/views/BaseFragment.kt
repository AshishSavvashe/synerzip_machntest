package demo.com.synerzip_ashish_savvashe.views

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return null
    }

    /*
    * To check internet connectivity on api or network call
    * */
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT < 23) {
            val ni = cm.activeNetworkInfo

            if (ni != null) {
                return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
            }
        } else {
            val n = cm.activeNetwork

            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)

                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            }
        }

        return false
    }

    /*
    * Common snack message for success or warning
    * */
    fun showSnack(view: View?, msg: String?) {
        Snackbar.make(view!!,msg!!, Snackbar.LENGTH_SHORT).show()
    }

    /*
    * Set MainActivity toolbar title
    * */
    fun setTitle(title: String) {
        (context as MainActivity).setToolBarTitle(title)
    }
}
