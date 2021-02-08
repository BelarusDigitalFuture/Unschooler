package unschooler.code.utils

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.res.Resources
import android.text.Editable
import android.view.View
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import unschooler.code.R

fun EditText.connect(
    viewLifecycleOwner: LifecycleOwner,
    liveData: MutableLiveData<String>
) {

    liveData.observe(viewLifecycleOwner, Observer { value ->
        if (this.editableText.toString() != value)
            this.setText(value)
    })

    this.addTextChangedListener(
        afterTextChanged = {
            if (it.toString() != liveData.value) {
                liveData.postValue(it.toString())
            }
        }
    )
}

@ExperimentalCoroutinesApi
fun EditText.asFlow() = callbackFlow {
    val afterTextChanged: (Editable?) -> Unit = {
        offer(text.toString())
    }
    val textChangedListener = addTextChangedListener(afterTextChanged = afterTextChanged)

    awaitClose {
        removeTextChangedListener(textChangedListener)
    }
}


fun CheckBox.connect(
    viewLifecycleOwner: LifecycleOwner,
    liveData: MutableLiveData<Boolean>
) {
    liveData.observe(viewLifecycleOwner, Observer { value ->
        if (this.isChecked != value)
            this.isChecked = value
    })
    this.setOnCheckedChangeListener { buttonView, isChecked ->
        if (isChecked != liveData.value) {
            liveData.postValue(isChecked)
        }
    }
}

fun View.visibleOrGone(visible: Boolean) {
    if (visible)
        this.visibility = VISIBLE
    else
        this.visibility = GONE
}

fun View.visibleOrInvisible(visible: Boolean) {
    if (visible)
        this.visibility = VISIBLE
    else
        this.visibility = INVISIBLE
}

fun Activity.alertOk(
    message: Int,
    title: Int = R.string.app_name,
    cancelable: Boolean = true,
    okListener: () -> Unit = {}
) {
    MaterialAlertDialogBuilder(ContextThemeWrapper(this, R.style.AlertDialog))
        .setTitle(title)
        .setMessage(message)
        .setCancelable(cancelable)
        .setPositiveButton(R.string.ui_ok) { dialog, which ->
            okListener.invoke()
        }
        .create()
        .apply {
            setCanceledOnTouchOutside(cancelable)
            show()
        }
}

fun Activity.alertOk(
    message: String,
    title: Int = R.string.app_name,
    cancelable: Boolean = true,
    okListener: () -> Unit = {}
) {
    MaterialAlertDialogBuilder(ContextThemeWrapper(this, R.style.AlertDialog))
        .setTitle(title)
        .setMessage(message)
        .setCancelable(cancelable)
        .setPositiveButton(R.string.ui_ok) { dialog, which ->
            okListener.invoke()
        }
        .create()
        .apply {
            setCanceledOnTouchOutside(cancelable)
            show()
        }
}

fun Fragment.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = Resources.getSystem().getDimensionPixelSize(resourceId)
    }
    return result
}

//fun View.vibrate(millis: Long = 300L) {
//    (this.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?)?.vibrate(millis)
//}

fun View.hideKeyboardFrom() {
    val imm = this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.shakeAnimation() {
    ObjectAnimator.ofFloat(this, View.TRANSLATION_X, 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f).start()
}

fun View.waveAnimation() {
    this.animate().scaleX(1.0f).scaleY(1.0f).alpha(0.9f).setDuration(0).withEndAction {
        this.animate().scaleX(1.9f).scaleY(1.9f).alpha(0f).setStartDelay(1000).setDuration(1000).withEndAction(this::waveAnimation).start()
    }.start()
}


const val VIBRATE_SHOT = 10L


