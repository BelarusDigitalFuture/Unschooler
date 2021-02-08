package unschooler.code.ui.main

import androidx.lifecycle.ViewModel
import unschooler.code.managers.auth.AuthRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    val isAuth = authRepository.isAuth
}