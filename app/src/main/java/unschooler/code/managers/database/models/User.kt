package unschooler.code.managers.database.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User @JvmOverloads constructor(
    val name: String? = null,
    val email: String? = null,
    val picture: String? = null,
    val description: String = "Биология",
    private val allLikes: Coins? = null,
    private val allCoins: Likes? = null
) {

    val coins
        get() = allLikes

    val likes
        get() = allCoins

}


data class Coins(val all: Int? = null, val new: Int? = null)

data class Likes(val all: Int? = null, val new: Int? = null)