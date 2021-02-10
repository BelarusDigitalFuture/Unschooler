package unschooler.code.managers.database.models

class AnswerObject(
    var id: String,
    val creator: User,
    val theme : AnswerThemeObject,
    val title: String,
    val description: String?,
    val video: String?
)

class AnswerThemeObject(
    val themeId: String,
    val themeName: String,
)