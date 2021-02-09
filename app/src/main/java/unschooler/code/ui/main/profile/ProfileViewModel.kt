package unschooler.code.ui.main.profile

import unschooler.code.arch.BaseViewModel
import unschooler.code.managers.auth.AuthRepository
import unschooler.code.managers.database.Database
import unschooler.code.utils.CombineLiveData
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val database: Database,
) : BaseViewModel() {

    val user = database.user

    val  answers = database.answers

    val myAnswers = CombineLiveData(database.user, database.answers) { user, answers ->
        if (user != null && answers != null){
            val myAnswers = user.myAnswers
            answers.filter { myAnswers.contains(it.id) }
        } else {
            emptyList()
        }
    }

    val likedAnswers = CombineLiveData(database.user, database.answers) { user, answers ->
        if (user != null && answers != null){
            val likedAnswers = user.likedAnswers
            answers.filter { likedAnswers.contains(it.id) }
        } else {
            emptyList()
        }
    }
}
