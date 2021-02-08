package unschooler.code.ui.main.schedule

import androidx.lifecycle.MutableLiveData
import unschooler.code.arch.BaseViewModel
import unschooler.code.ui.main.schedule.common.Subject
import unschooler.code.ui.main.schedule.common.Theme
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
) : BaseViewModel() {

    val subjects = MutableLiveData((0..10).map {
        Subject("Предмет $it", (0..10).map {
            Theme("Тема $it")
        })
    })
}
