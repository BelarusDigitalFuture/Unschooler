package unschooler.code.ui.main.themes.theme

import unschooler.code.arch.BaseViewModel
import unschooler.code.managers.database.Database
import unschooler.code.managers.database.models.Answer
import unschooler.code.ui.main.themes.common.Subject
import unschooler.code.ui.main.themes.common.Theme
import javax.inject.Inject

class ThemeViewModel @Inject constructor(
    val database: Database
) : BaseViewModel() {

    fun getSubject(theme: String) : Subject?{
        return database.subjects.value?.find { it.themes.find { it.id == theme } != null }
    }

    fun getAnswer(answer: String) : Answer? {
        return database.answers.value?.find { it.id == answer }
    }
}
