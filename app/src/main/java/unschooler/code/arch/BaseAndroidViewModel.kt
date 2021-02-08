package unschooler.code.arch

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import unschooler.code.utils.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import unschooler.code.utils.Result
import unschooler.code.utils.toEvent

open class BaseAndroidViewModel(
    application: Application
) : AndroidViewModel(application) {

    protected val _baseErrorText = MutableLiveData<Event<String>>()
    val baseErrorText: LiveData<Event<String>> = _baseErrorText

    protected val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    val handler = CoroutineExceptionHandler {
            context, exception ->
        Toast.makeText(getApplication(),"Caught $exception", Toast.LENGTH_LONG).show()
    }

    fun <T> makeRequest(function: suspend () -> Result<T>, result: (T) -> Unit) {
        _isLoading.postValue(true)
        viewModelScope.launch(handler) {
            function.invoke().proceedResult(
                success = {
                    result.invoke(it)
                },
                error = {
                    _baseErrorText.postValue(it.toEvent())
                }
            )
            _isLoading.postValue(false)
        }
    }

    fun getString(stringId: Int) = getApplication<Application>().getString(stringId)

    fun getString(stringId: Int, vararg values: String) = getApplication<Application>().getString(stringId, *values)
}