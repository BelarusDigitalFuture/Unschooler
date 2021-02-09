package unschooler.code.managers.database.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User @JvmOverloads constructor(
    val uid: String,
    val name: String? = null,
    val email: String? = null,
    val picture: String? = null,
    val description: String? = null,
    private val allLikes: Coins? = null,
    private val allCoins: Likes? = null,
    private val themesFavorite: HashMap<String, String>? = null,
    private val answersList: HashMap<String, String>? = null,
    private val answersLiked: HashMap<String, String>? = null
) {

    val coins
        get() = allLikes

    val likes
        get() = allCoins

    val likedThemes
        get() = themesFavorite?.keys ?: hashSetOf()

    val likedAnswers
        get() = answersLiked?.keys ?: hashSetOf()

    val myAnswers
        get() = answersList?.keys ?: hashSetOf()
}


data class Coins(val all: Int? = null, val new: Int? = null)

data class Likes(val all: Int? = null, val new: Int? = null)