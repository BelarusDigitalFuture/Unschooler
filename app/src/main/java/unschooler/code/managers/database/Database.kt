package unschooler.code.managers.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import org.json.JSONObject
import unschooler.code.managers.auth.AuthRepository
import unschooler.code.managers.database.models.Answer
import unschooler.code.managers.database.models.AnswerObject
import unschooler.code.managers.database.models.ThemeObject
import unschooler.code.managers.database.models.User
import unschooler.code.ui.main.themes.common.Subject
import unschooler.code.ui.main.themes.common.Theme
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

    private val _subjects = MutableLiveData<List<Subject>>()
    val subjects: LiveData<List<Subject>> = _subjects

    private val _answers = MutableLiveData<List<Answer>>()
    val answers: LiveData<List<Answer>> = _answers


    init {
        authRepository.firebaseUserLiveData.observeForever { user ->
            if (user != null) {
                loadUser(user)
            }
        }

        user.observeForever {
            if (it != null) {
                loadThemes(it)
                loadAnswers()
            }
        }
    }

    private fun loadAnswers() {
        database.child("answers").get().addOnCompleteListener { task ->
            val map = task.result?.value as HashMap<String, *>
            val answers = map.map {
                gson.fromJson(
                    JSONObject(it.value as HashMap<*, *>).toString(),
                    AnswerObject::class.java
                ).apply {
                    this.id = it.key
                }
            }
            _answers.postValue(answers.map {
                Answer(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    video = it.video,
                    creator = it.creator,
                    themeId = it.theme.themeId,
                    themeName = it.theme.themeName
                )
            })
        }
    }

    private fun loadThemes(user: User) {
        val likedThemes = user.likedThemes
        database.child("themes").get().addOnCompleteListener { task ->
            val map = task.result?.value as HashMap<String, *>
            val themes = map.map {
                gson.fromJson(
                    JSONObject(it.value as HashMap<*, *>).toString(),
                    ThemeObject::class.java
                ).apply {
                    this.id = it.key
                }
            }
            _subjects.postValue(themes.groupBy { it.subject.id }.map {
                Subject(
                    id = it.key,
                    title = it.value[0].subject.subjectName,
                    themes = it.value.map { theme ->
                        Theme(
                            id = theme.id,
                            title = theme.themeName,
                            description = theme.themeDescription,
                            isLearned = likedThemes.contains(theme.id)
                        )
                    }
                )
            }.sortedBy { it.title })
        }
    }

    private fun loadUser(user: FirebaseUser) {
        database.child("users").child(user.uid).get().addOnCompleteListener { task ->
            _user.postValue(
                gson.fromJson(
                    JSONObject(task.result?.value as HashMap<*, *>).toString(),
                    User::class.java
                )
            )
        }
    }

    fun getUserInfo(id: String, listener: (User) -> Unit) {
        database.child("users").child(id).get().addOnCompleteListener { task ->
            val user = gson.fromJson(
                JSONObject(task.result?.value as HashMap<*, *>).toString(),
                User::class.java
            )
            listener.invoke(user)
        }
    }

    fun isMe(uid: String): Boolean {
        return user.value?.uid == uid
    }
}


//createDate:
//"2021-2-9 21:6:48"
//creator
//name:
//"KuSu Mikhail"
//picture:
//"https://lh3.googleusercontent.com/-dX1QGU63Jzc/..."
//uid:
//"b808upGzhkhfoXUut7lruLHkrm23"
//description:
//"Объяснение с видео"
//id:
//"-MT6yM4nspUtj_zDKsNJ"
//theme
//themeId:
//"-MPdmqr5wyiAB4EIWEe3"
//themeName:
//"Корень n-й степени из числа а (n ≥2, n ∈ N)"
//title:
//"Заголовок для объяснения с видео"
//video:
//"https://youtu.be/lkQ0LDx9jHs"
