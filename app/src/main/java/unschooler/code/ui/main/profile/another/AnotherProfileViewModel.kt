package unschooler.code.ui.main.profile.another

import unschooler.code.arch.BaseViewModel
import unschooler.code.managers.auth.AuthRepository
import unschooler.code.managers.database.Database
import unschooler.code.managers.database.models.Answer
import unschooler.code.managers.database.models.User
import unschooler.code.utils.CombineLiveData
import javax.inject.Inject

class AnotherProfileViewModel @Inject constructor(
    private val database: Database,
) : BaseViewModel() {

    fun getAnswers(id: String): List<Answer> {
        return database.answers.value?.filter { it.creator.uid == id } ?: emptyList()
    }

    fun getUser(id: String, listener: (User) -> Unit) {
        database.getUserInfo(id, listener)
    }
}
