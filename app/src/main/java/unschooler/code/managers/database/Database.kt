package unschooler.code.managers.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import org.json.JSONObject
import unschooler.code.managers.auth.AuthRepository
import unschooler.code.managers.database.models.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Database @Inject constructor(
    private val authRepository: AuthRepository
) {

    private val gson = Gson()

    private val database = FirebaseDatabase.getInstance().let {
        it.setPersistenceEnabled(false)
        it.reference
    }

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user


    init {
        authRepository.firebaseUserLiveData.observeForever { user ->
            if (user != null) {
                database.child("users").child(user.uid).get().addOnCompleteListener { task ->
                    _user.postValue(gson.fromJson(JSONObject(task.result?.value as HashMap<*,*>).toString(), User::class.java))
                }
            }
        }
    }
}