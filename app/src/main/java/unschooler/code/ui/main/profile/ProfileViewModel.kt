package unschooler.code.ui.main.profile

import unschooler.code.arch.BaseViewModel
import unschooler.code.managers.auth.AuthRepository
import unschooler.code.managers.database.Database
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val database: Database
) : BaseViewModel() {

    val user = database.user
}
