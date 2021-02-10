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

    private fun getAnswers(theme: String): List<Answer> {
        return database.answers.value?.filter { it.themeId == theme } ?: emptyList()
    }

    fun getAnswer(theme: String, answer: String?) : Answer? {
        return getAnswers(theme).find { it.id == answer } ?: getAnswers(theme).firstOrNull()
    }

    fun getAnswersList(theme: String, answer: String?): List<Answer> {
        return getAnswers(theme).toMutableList().apply {
            this.remove(getAnswer(theme, answer))
        }
    }

    fun isMe(uid: String): Boolean {
        return database.isMe(uid)
    }
}
