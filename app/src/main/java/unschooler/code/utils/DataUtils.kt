package unschooler.code.utils

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.roundToInt


fun String?.isValidEmail(): Boolean {
    return !this.isNullOrBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String?.isValidPhone(): Boolean {
    return !this.isNullOrBlank() && android.util.Patterns.PHONE.matcher(this).find()
}

fun Exception.toEvent(): Event<String> {
    return Event(localizedMessage ?: message ?: "Error")
}

fun <T> T.toEvent(): Event<T> {
    return Event(this)
}

fun String.addMark(mark: String = "*") = "$this $mark"

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun <K, V> Map<K, V?>.filterNotNullValues(): Map<K, V> {
    return mapNotNull { (key, nullableValue) ->
        nullableValue?.let { key to it }
    }.toMap()
}

fun <E> List<List<E>>.sum(): List<E> {
    val result = emptyList<E>().toMutableList()
    this.forEach { result += it }
    return result
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Context.generateTempFile(bitmap: Bitmap?, name: String): File {
    val f = File(cacheDir, name)
    f.createNewFile()
    val bos = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, bos)
    f.writeBytes(bos.toByteArray())

    return f
}

fun Float.toMinString(): String {
    if (this == this.roundToInt().toFloat())
        return this.roundToInt().toString()
    return this.toString()
}

fun Double.toMinString(): String {
    if (this == this.roundToInt().toDouble())
        return this.roundToInt().toString()
    return this.toString()
}

fun Double.toMinString(round: Int): String {
//    if (this == this.roundToInt().toDouble())
//        return this.roundToInt().toString()
    val s = DecimalFormatSymbols(Locale.US)
    s.groupingSeparator = ' '
    val form = DecimalFormat()
    form.groupingSize = 3
    form.maximumFractionDigits = round
//    form.minimumFractionDigits = this?.scale() ?: 1
    form.decimalFormatSymbols = s
    return form.format(this)
}

private fun getImageRealPath(contentResolver: ContentResolver, uri: Uri): String? {
    var ret = ""
    // Query the uri with condition.
    val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
    if (cursor != null) {
        val moveToFirst: Boolean = cursor.moveToFirst()
        if (moveToFirst) {
            // Get columns name by uri type.
            var columnName = MediaStore.Images.Media.DATA
            if (uri === MediaStore.Images.Media.EXTERNAL_CONTENT_URI) {
                columnName = MediaStore.Images.Media.DATA
            } else if (uri === MediaStore.Audio.Media.EXTERNAL_CONTENT_URI) {
                columnName = MediaStore.Audio.Media.DATA
            } else if (uri === MediaStore.Video.Media.EXTERNAL_CONTENT_URI) {
                columnName = MediaStore.Video.Media.DATA
            }
            // Get column index.
            val imageColumnIndex: Int = cursor.getColumnIndex(columnName)
            // Get column value which is the uri related file local path.
            ret = cursor.getString(imageColumnIndex)
        }
    }
    return ret
}

fun Bitmap.createTempFile(): File {
    val file = File.createTempFile("temp", "jpg")
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    file.writeBytes(stream.toByteArray())
    return file
}