package lobna.parentaps.daily.forecast.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment

object IntentClass {
    fun goToActivity(
        activity: Activity,
        target: Class<*>,
        value: Bundle? = null,
        compat: ActivityOptionsCompat? = null,
        action: String? = null,
        enterAnim: Int = -1,
        exitAnim: Int = -1,
        clear: Boolean = false,
        top: Boolean = false,
        finish: Boolean = false
    ) {
        val intent = Intent(activity, target)
        if (clear)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        if (top)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("data", value)
        intent.action = action
        activity.startActivity(intent, compat?.toBundle())
        if (enterAnim != -1 && exitAnim != -1)
            activity.overridePendingTransition(enterAnim, exitAnim)

        if (finish) activity.finish()
    }

    fun goToActivity(
        context: Context,
        target: Class<*>,
        value: Bundle? = null,
        action: String? = null,
        clear: Boolean = false,
        top: Boolean = false
    ) {
        val intent = Intent(context, target)
        if (clear)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        if (top)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("data", value)
        intent.action = action
        context.startActivity(intent)
    }

    fun goToActivityForResult(
        activity: Activity,
        target: Class<*>,
        value: Bundle? = null,
        action: String? = null,
        requestCode: Int,
        enterAnim: Int = -1,
        exitAnim: Int = -1
    ) {
        val intent = Intent(activity, target)
        intent.putExtra("data", value)
        intent.action = action
        activity.startActivityForResult(intent, requestCode)
        if (enterAnim != -1 && exitAnim != -1)
            activity.overridePendingTransition(enterAnim, exitAnim)
    }

    fun goToActivityForResult(
        fragment: Fragment,
        target: Class<*>,
        value: Bundle? = null,
        action: String? = null,
        requestCode: Int
    ) {
        val intent = Intent(fragment.context, target)
        intent.putExtra("data", value)
        intent.action = action
        fragment.startActivityForResult(intent, requestCode)
    }

    fun makeCall(context: Context, phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${phone}")
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun sendEmail(context: Context, email: String) {
        val emailIntent = Intent(
            Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null
            )
        )
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{address}); // String[] addresses
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."))

    }

    fun goToLink(context: Context, link: String) {
        if (link.isNotBlank()) {
            val uri = Uri.parse(link)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }

    fun share(context: Context, text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

    fun navigateOnMaps(context: Context, latitude: Double?, longitude: Double?) {
        val gmmIntentUri = Uri.parse("geo:${latitude},${longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.resolveActivity(context.packageManager)?.let {
            context.startActivity(mapIntent)
        }
    }
}