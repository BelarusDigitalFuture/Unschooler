package unschooler.code.ui.main.themes

import unschooler.code.arch.BaseViewModel
import unschooler.code.managers.database.Database
import javax.inject.Inject

class ThemesViewModel @Inject constructor(
    val database: Database
) : BaseViewModel() {

    val subjects = database.subjects
}
