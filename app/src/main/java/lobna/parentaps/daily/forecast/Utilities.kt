package lobna.parentaps.daily.forecast

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.text.format.DateUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


object Utilities {

    private val TAG = Utilities.javaClass.simpleName

    fun showKeyboard(view: View) {
        try {
            val imm =
                view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideKeyboard(view: View) {
        try {
            val imm =
                view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideKeyboard(activity: Activity) {
        try {
            val imm =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(View(activity).windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showLoading(context: Context): AlertDialog {
        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.MyAlertDialogTheme))
        builder.setView(R.layout.loading_view)

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        return alertDialog
    }

    fun dismissLoading(alertDialog: AlertDialog) {
        alertDialog.dismiss()
    }

    private var toast: Toast? = null

    fun Context.showToast(s: String, length: Int = Toast.LENGTH_SHORT) {
        if (toast != null)
            toast!!.cancel()
        toast = Toast.makeText(this, s, length)
        toast!!.show()
    }

    fun Context.showToast(resId: Int, length: Int = Toast.LENGTH_SHORT) {
        showToast(getString(resId, length))
    }

    fun View.showSnackbar(s: String?) {
        if (s.isNullOrBlank()) return
        Snackbar.make(this, s, Snackbar.LENGTH_LONG)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .setBackgroundTint(ContextCompat.getColor(context, R.color.colorOrange))
            .setTextColor(ContextCompat.getColor(context, R.color.white))
            .show()
    }

    fun getJson(o: Any?): String? {
        return Gson().toJson(o)
    }

    fun Any.toJson(): String {
        return Gson().toJson(this)
    }

    fun getObjectFromJson(json: String, c: Class<*>?): Any? {
        return try {
            if (json.isEmpty()) null else Gson().fromJson(json, c)
        } catch (e: java.lang.Exception) {
            null
        }
    }

    fun String.toObjectFromJson(c: Class<*>?): Any? {
        return try {
            if (isEmpty()) null else Gson().fromJson(this, c)
        } catch (e: java.lang.Exception) {
            null
        }
    }


}