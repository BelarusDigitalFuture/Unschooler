package unschooler.code.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import unschooler.code.managers.auth.AuthRepository
import unschooler.code.managers.auth.FirebaseUserLiveData
import unschooler.code.managers.database.Database
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val database: Database
) : ViewModel() {

    val isAuth: LiveData<Boolean> = authRepository.isAuth
}