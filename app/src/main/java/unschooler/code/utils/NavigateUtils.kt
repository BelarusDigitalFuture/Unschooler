package unschooler.code.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import java.lang.Exception


fun NavController.navigateSafe(direction: NavDirections) {
    try {
        navigate(direction)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun NavController.navigateSafe(direction: Int) {
    try {
        navigate(direction)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
