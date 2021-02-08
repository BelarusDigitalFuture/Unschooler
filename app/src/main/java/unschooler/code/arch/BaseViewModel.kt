package unschooler.code.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import unschooler.code.utils.Event
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {

    protected val _baseErrorText = MutableLiveData<Event<String>>()
    val baseErrorText: LiveData<Event<String>> = _baseErrorText

    protected val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    val handler = CoroutineExceptionHandler {
            context, exception ->
        println("ExcM Caught $exception")
    }
}